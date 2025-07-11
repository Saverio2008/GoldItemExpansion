package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.*;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(SpriteLoader.class)
public class SpriteLoaderMixin {

    @Inject(
            method = "load(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/Identifier;ILjava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void onLoad(ResourceManager manager, Identifier atlasId, int mipLevel, Executor executor,
                        CallbackInfoReturnable<CompletableFuture<SpriteLoader.StitchResult>> cir) {
        if (!atlasId.equals(new Identifier("textures", "atlas/mob_effects.png"))) {
            return;
        }

        LOGGER.info("[GoldItemExpansion] Injecting custom mob effect icons...");

        CompletableFuture<SpriteLoader.StitchResult> originalFuture = cir.getReturnValue();

        CompletableFuture<SpriteLoader.StitchResult> newFuture = originalFuture.thenCompose(originalResult -> {
            try {
                Set<Identifier> originalIds = originalResult.regions().keySet();
                LOGGER.info("[GoldItemExpansion] Original sprite count: {}", originalIds.size());

                List<CompletableFuture<SpriteContents>> loadFutures = new ArrayList<>();
                for (Identifier id : originalIds) {
                    loadFutures.add(CompletableFuture.supplyAsync(() -> loadSpriteContents(manager, id), executor));
                }

                List<Identifier> customIds = List.of(
                        new Identifier("golditemexpansion", "mob_effects/god_positive_status_effect"),
                        new Identifier("golditemexpansion", "mob_effects/god_negative_status_effect")
                );

                for (Identifier customId : customIds) {
                    LOGGER.info("[GoldItemExpansion] Attempting to load custom icon: {}", customId);
                    loadFutures.add(CompletableFuture.supplyAsync(() -> loadSpriteContents(manager, customId), executor));
                }

                return CompletableFuture.allOf(loadFutures.toArray(new CompletableFuture[0]))
                        .thenApply(v -> {
                            List<SpriteContents> allContents = new ArrayList<>();
                            for (CompletableFuture<SpriteContents> f : loadFutures) {
                                SpriteContents c = f.join();
                                if (c != null) {
                                    LOGGER.info("[GoldItemExpansion] Sprite loaded: {}", c.getId());
                                    allContents.add(c);
                                }
                            }

                            SpriteLoader self = (SpriteLoader)(Object)this;
                            LOGGER.info("[GoldItemExpansion] Final stitching {} sprites...", allContents.size());
                            return self.stitch(allContents, mipLevel, executor);
                        });

            } catch (Exception e) {
                LOGGER.error("Failed to reload sprites with custom additions", e);
                return CompletableFuture.completedFuture(originalResult);
            }
        });

        cir.setReturnValue(newFuture);
    }
    @Unique
    private SpriteContents loadSpriteContents(ResourceManager manager, Identifier id) {
        try {
            Identifier resourceId = idToResourceId(id);
            Optional<Resource> optional = manager.getResource(resourceId);
            if (optional.isEmpty()) {
                LOGGER.error("[GoldItemExpansion] ❌ Texture not found: {}", resourceId);
                return null;
            }

            Resource resource = optional.get();
            AnimationResourceMetadata animationMetadata;
            try {
                animationMetadata = resource.getMetadata().decode(AnimationResourceMetadata.READER).orElse(AnimationResourceMetadata.EMPTY);
            } catch (Exception e) {
                LOGGER.error("❌ Metadata error for {}", id, e);
                return null;
            }

            NativeImage image;
            try (InputStream input = resource.getInputStream()) {
                image = NativeImage.read(input);
            }

            SpriteDimensions dimensions = animationMetadata.getSize(image.getWidth(), image.getHeight());

            if (image.getWidth() % dimensions.width() != 0 || image.getHeight() % dimensions.height() != 0) {
                LOGGER.error("❌ Image {} size {},{} is not multiple of frame size {},{}", id, image.getWidth(), image.getHeight(), dimensions.width(), dimensions.height());
                image.close();
                return null;
            }

            return new SpriteContents(id, dimensions, image, animationMetadata);

        } catch (Exception e) {
            LOGGER.error("❌ Failed to load sprite contents for {}", id, e);
            return null;
        }
    }
    @Unique
    private Identifier idToResourceId(Identifier id) {
        return new Identifier(id.getNamespace(), "textures/" + id.getPath() + ".png");
    }
}
