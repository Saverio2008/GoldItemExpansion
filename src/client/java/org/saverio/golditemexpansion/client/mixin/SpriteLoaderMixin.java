package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.texture.SpriteLoader;
import net.minecraft.client.texture.SpriteLoader.StitchResult;
import net.minecraft.resource.Resource;
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
    private void injectCustomMobEffectSprites(
            ResourceManager resourceManager,
            Identifier atlasId,
            int mipLevel,
            Executor executor,
            CallbackInfoReturnable<CompletableFuture<StitchResult>> cir
    ) {
        // 只注入到 mob_effects 图集中
        if (!atlasId.equals(new Identifier("minecraft", "mob_effects"))) {
            return;
        }

        CompletableFuture<StitchResult> originalFuture = cir.getReturnValue();

        CompletableFuture<StitchResult> newFuture = originalFuture.thenCompose(originalResult -> {
            List<SpriteContents> contentsList = new ArrayList<>(originalResult.regions().size());
            for (Sprite sprite : originalResult.regions().values()) {
                contentsList.add(sprite.getContents());
            }

            // 自定义图标（不包含 textures/ 前缀和 .png 后缀）
            List<Identifier> customIds = List.of(
                    new Identifier("golditemexpansion", "god_positive_status_effect"),
                    new Identifier("golditemexpansion", "god_negative_status_effect")
            );

            List<CompletableFuture<SpriteContents>> loadFutures = new ArrayList<>();

            for (Identifier id : customIds) {
                loadFutures.add(CompletableFuture.supplyAsync(() -> loadSprite(resourceManager, id), executor));
            }

            return CompletableFuture.allOf(loadFutures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> {
                        for (CompletableFuture<SpriteContents> future : loadFutures) {
                            SpriteContents contents = future.join();
                            if (contents != null) {
                                contentsList.add(contents);
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
            System.out.println("[GoldItemExpansion] 🔍 Trying to load sprite: " + id);
            // 自动加上 textures 路径和 .png 后缀
            Identifier texturePath = new Identifier(id.getNamespace(), "textures/mob_effects/" + id.getPath() + ".png");

            var optionalResource = manager.getResource(texturePath);
            if (optionalResource.isEmpty()) {
                System.out.println("[GoldItemExpansion] ❌ Resource not found: " + texturePath);
                return null;
            }

            Resource resource = optionalResource.get();
            SpriteContents contents = SpriteLoader.load(id, resource);

            if (contents == null) {
                System.out.println("[GoldItemExpansion] ❌ Failed to load sprite: " + id);
            } else {
                System.out.println("[GoldItemExpansion] ✅ Successfully loaded sprite: " + id);
            }

            return contents;

        } catch (Exception e) {
            System.out.println("[GoldItemExpansion] ❌ Exception loading sprite " + id + ": " + e.getMessage());
            LOGGER.error("Failed to load custom sprite: {}", id, e);
            return null;
        }
    }
}