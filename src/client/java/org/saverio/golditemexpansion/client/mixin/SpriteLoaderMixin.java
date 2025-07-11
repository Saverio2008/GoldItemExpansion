package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.texture.SpriteLoader;
import net.minecraft.client.texture.SpriteLoader.StitchResult;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(SpriteLoader.class)
public class SpriteLoaderMixin {

    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "load*", at = @At("RETURN"), cancellable = true)
    private void onLoad(ResourceManager resourceManager, Identifier path, int mipLevel, Executor executor, CallbackInfoReturnable<CompletableFuture<StitchResult>> cir) {
        if (!path.equals(new Identifier("minecraft", "mob_effects"))) {
            return;
        }
        CompletableFuture<StitchResult> originalFuture = cir.getReturnValue();
        CompletableFuture<StitchResult> newFuture = originalFuture.thenCompose(originalResult -> {
            List<SpriteContents> contentsList = new ArrayList<>(originalResult.regions().size());
            for (Sprite sprite : originalResult.regions().values()) {
                contentsList.add(sprite.getContents());
            }
            List<Identifier> customIds = List.of(
                    new Identifier("golditemexpansion", "mob_effects/god_positive_status_effect"),
                    new Identifier("golditemexpansion", "mob_effects/god_negative_status_effect")
            );
            List<CompletableFuture<SpriteContents>> loadFutures = new ArrayList<>();
            for (Identifier id : customIds) {
                loadFutures.add(CompletableFuture.supplyAsync(() -> loadSprite(resourceManager, id), executor));
            }
            return CompletableFuture.allOf(loadFutures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> {
                        for (CompletableFuture<SpriteContents> f : loadFutures) {
                            SpriteContents c = f.join();
                            if (c != null) {
                                contentsList.add(c);
                            }
                        }

                        SpriteLoader self = (SpriteLoader) (Object) this;
                        return self.stitch(contentsList, mipLevel, executor);
                    });
        });
        cir.setReturnValue(newFuture);
    }

    @Unique
    private SpriteContents loadSprite(ResourceManager manager, Identifier id) {
        try {
            Identifier resourceId = new Identifier(id.getNamespace(), "textures/" + id.getPath() + ".png");

            var optionalResource = manager.getResource(resourceId);
            if (optionalResource.isEmpty()) {
                System.out.println("[GoldItemExpansion] ❌ Resource not found: " + resourceId);
                return null;
            }

            SpriteContents spriteContents = SpriteLoader.load(id, optionalResource.get());
            if (spriteContents == null) {
                System.out.println("[GoldItemExpansion] ❌ Failed to load SpriteContents for: " + resourceId);
            } else {
                System.out.println("[GoldItemExpansion] ✅ Successfully loaded sprite: " + id);
            }

            return spriteContents;
        } catch (Exception e) {
            System.out.println("[GoldItemExpansion] ❌ Exception loading sprite for " + id);
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}