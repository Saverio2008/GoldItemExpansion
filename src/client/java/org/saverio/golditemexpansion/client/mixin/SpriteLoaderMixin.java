package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.*;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.client.mixin.accessor.SpriteInvoker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    private void onLoad(ResourceManager manager, Identifier atlasId, int mipLevel, Executor executor, CallbackInfoReturnable<CompletableFuture<SpriteLoader.StitchResult>> cir) {
        CompletableFuture<SpriteLoader.StitchResult> originalFuture = cir.getReturnValue();

        if (!atlasId.equals(new Identifier("textures", "atlas/mob_effects.png"))) {
            return;
        }

        CompletableFuture<SpriteLoader.StitchResult> newFuture = originalFuture.thenApply(originalResult -> {
            Map<Identifier, Sprite> originalRegions = new HashMap<>(originalResult.regions());

            Identifier positiveId = new Identifier("golditemexpansion", "mob_effects/god_positive_status_effect");
            Sprite positiveSprite = loadCustomSprite(manager, positiveId, mipLevel);
            if (positiveSprite != null) {
                originalRegions.put(positiveId, positiveSprite);
            }

            Identifier negativeId = new Identifier("golditemexpansion", "mob_effects/god_negative_status_effect");
            Sprite negativeSprite = loadCustomSprite(manager, negativeId, mipLevel);
            if (negativeSprite != null) {
                originalRegions.put(negativeId, negativeSprite);
            }

            return new SpriteLoader.StitchResult(
                    originalResult.width(),
                    originalResult.height(),
                    originalResult.mipLevel(),
                    originalResult.missing(),
                    Map.copyOf(originalRegions),
                    originalResult.readyForUpload()
            );
        });

        cir.setReturnValue(newFuture);
    }
    @Unique
    private Sprite loadCustomSprite(ResourceManager manager, Identifier id, int mipLevel) {
        try {
            Optional<Resource> optional = manager.getResource(new Identifier(id.getNamespace(), "textures/" + id.getPath() + ".png"));
            if (optional.isEmpty()) {
                System.err.println("[GoldItemExpansion] Texture not found: " + id);
                return null;
            }
            NativeImage image;
            try (InputStream input = optional.get().getInputStream()) {
                image = NativeImage.read(input);
            }
            int width = image.getWidth();
            int height = image.getHeight();
            SpriteContents contents = new SpriteContents(
                    id,
                    new SpriteDimensions(width, height),
                    image,
                    AnimationResourceMetadata.EMPTY
            );
            Identifier atlasId = new Identifier("textures/atlas/mob_effects.png");
            return SpriteInvoker.invokeInit(
                    atlasId,
                    contents,
                    width,
                    height,
                    0,
                    0
            );
        } catch (Exception e) {
            LOGGER.error("Failed to load sprite: {}", id, e);
            return null;
        }
    }
}
