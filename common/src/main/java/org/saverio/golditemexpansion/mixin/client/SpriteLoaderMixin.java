package org.saverio.golditemexpansion.mixin.client;

import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

@Mixin(SpriteLoader.class)
public final class SpriteLoaderMixin {

    @Final
    @Unique
    private static final ResourceLocation MOB_EFFECTS_ATLAS = new ResourceLocation("minecraft", "mob_effects");

    @Final
    @Unique
    private static final Logger golditemexpansion$LOGGER = org.slf4j.LoggerFactory.getLogger("SpriteLoaderMixin");

    @Inject(method = "loadAndStitch", at = @At("RETURN"), cancellable = true)
    private void injectCustomSprites(ResourceManager resourceManager,
                                     ResourceLocation atlasId,
                                     int mipLevel,
                                     Executor executor,
                                     CallbackInfoReturnable<CompletableFuture<SpriteLoader.Preparations>> cir) {
        if (!MOB_EFFECTS_ATLAS.equals(atlasId)) return;

        CompletableFuture<SpriteLoader.Preparations> originalFuture = cir.getReturnValue();

        CompletableFuture<SpriteLoader.Preparations> newFuture = originalFuture.thenCompose(preparations -> {
            List<SpriteContents> allSprites = new ArrayList<>(preparations.regions().values().stream()
                    .map(TextureAtlasSprite::contents)
                    .toList());

            List<ResourceLocation> customIds = List.of(
                    new ResourceLocation(MOD_ID, "god_positive_effect"),
                    new ResourceLocation(MOD_ID, "god_negative_effect")
            );

            List<CompletableFuture<SpriteContents>> futures = new ArrayList<>();
            for (ResourceLocation id : customIds) {
                futures.add(CompletableFuture.supplyAsync(() -> golditemexpansion$loadCustomSprite(resourceManager, id), executor));
            }

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).thenApply(v -> {
                for (CompletableFuture<SpriteContents> future : futures) {
                    SpriteContents contents = future.join();
                    if (contents != null) {
                        allSprites.add(contents);
                    }
                }

                SpriteLoader self = (SpriteLoader) (Object) this;
                return self.stitch(allSprites, mipLevel, executor);
            });
        });

        cir.setReturnValue(newFuture);
    }

    @Unique
    private SpriteContents golditemexpansion$loadCustomSprite(ResourceManager resourceManager, ResourceLocation id) {
        ResourceLocation texturePath = new ResourceLocation(id.getNamespace(), "textures/mob_effects/" + id.getPath() + ".png");

        try {
            var optional = resourceManager.getResource(texturePath);
            if (optional.isEmpty()) {
                golditemexpansion$LOGGER.warn("Custom sprite not found: {}", texturePath);
                return null;
            }

            Resource resource = optional.get();
            return SpriteLoader.loadSprite(id, resource);

        } catch (Exception e) {
            golditemexpansion$LOGGER.error("Failed to load sprite: {}", texturePath, e);
            return null;
        }
    }
}