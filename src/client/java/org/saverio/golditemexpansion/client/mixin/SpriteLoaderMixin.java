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
        // 只对 mob_effects 图集注入
        if (!atlasId.equals(new Identifier("minecraft", "mob_effects"))) {
            return;
        }

        LOGGER.info("[GoldItemExpansion] Injecting custom mob effect icons only...");

        CompletableFuture<SpriteLoader.StitchResult> originalFuture = cir.getReturnValue();

        CompletableFuture<SpriteLoader.StitchResult> newFuture = originalFuture.thenCompose(originalResult -> {
            try {
                // 只加载自定义贴图
                List<Identifier> customIds = List.of(
                        new Identifier("golditemexpansion", "mob_effects/god_positive_status_effect"),
                        new Identifier("golditemexpansion", "mob_effects/god_negative_status_effect")
                );

                List<CompletableFuture<SpriteContents>> customLoadFutures = new ArrayList<>();
                for (Identifier id : customIds) {
                    LOGGER.info("[GoldItemExpansion] Loading custom icon: {}", id);
                    customLoadFutures.add(CompletableFuture.supplyAsync(() -> loadSpriteContents(manager, id), executor));
                }

                return CompletableFuture.allOf(customLoadFutures.toArray(new CompletableFuture[0]))
                        .thenApply(v -> {
                            List<SpriteContents> customContents = new ArrayList<>();
                            for (int i = 0; i < customLoadFutures.size(); i++) {
                                SpriteContents content = customLoadFutures.get(i).join();
                                if (content != null) {
                                    LOGGER.info("[GoldItemExpansion] Custom sprite loaded: {}", content.getId());
                                    customContents.add(content);
                                } else {
                                    LOGGER.error("[GoldItemExpansion] Failed to load custom sprite: {}", customIds.get(i));
                                }
                            }

                            // 只缝合自定义的 SpriteContents，不混合原版
                            SpriteLoader self = (SpriteLoader)(Object)this;
                            LOGGER.info("[GoldItemExpansion] Stitching custom sprites total: {}", customContents.size());
                            return self.stitch(customContents, mipLevel, executor);
                        });

            } catch (Exception e) {
                LOGGER.error("[GoldItemExpansion] Failed to inject custom sprites, fallback to original", e);
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
                LOGGER.error("[GoldItemExpansion] Texture not found: {}", resourceId);
                return null;
            }

            Resource resource = optional.get();
            AnimationResourceMetadata animationMetadata;
            try {
                animationMetadata = resource.getMetadata().decode(AnimationResourceMetadata.READER).orElse(AnimationResourceMetadata.EMPTY);
            } catch (Exception e) {
                LOGGER.error("[GoldItemExpansion] Metadata error for {}", id, e);
                return null;
            }

            NativeImage image;
            try (InputStream input = resource.getInputStream()) {
                image = NativeImage.read(input);
            }

            SpriteDimensions dimensions = animationMetadata.getSize(image.getWidth(), image.getHeight());

            if (image.getWidth() % dimensions.width() != 0 || image.getHeight() % dimensions.height() != 0) {
                LOGGER.error("[GoldItemExpansion] Image {} size {},{} not multiple of frame size {},{}", id, image.getWidth(), image.getHeight(), dimensions.width(), dimensions.height());
                image.close();
                return null;
            }

            return new SpriteContents(id, dimensions, image, animationMetadata);

        } catch (Exception e) {
            LOGGER.error("[GoldItemExpansion] Failed to load sprite contents for {}", id, e);
            return null;
        }
    }

    @Unique
    private Identifier idToResourceId(Identifier id) {
        return new Identifier(id.getNamespace(), "textures/" + id.getPath() + ".png");
    }
}