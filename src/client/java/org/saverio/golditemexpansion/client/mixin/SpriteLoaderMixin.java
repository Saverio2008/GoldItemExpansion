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
        if (!atlasId.equals(new Identifier("minecraft", "mob_effects"))) {
            return;
        }

        CompletableFuture<StitchResult> originalFuture = cir.getReturnValue();

        CompletableFuture<StitchResult> newFuture = originalFuture.thenCompose(originalResult -> {
            List<SpriteContents> contentsList = new ArrayList<>(originalResult.regions().size());

            // 1. 打印原始图集已有的 SpriteContents 并加入列表
            System.out.println("[GoldItemExpansion] 原始图集已有贴图:");
            for (Sprite sprite : originalResult.regions().values()) {
                SpriteContents contents = sprite.getContents();
                System.out.println(" - " + (contents == null ? "null" : contents.getId()));
                contentsList.add(contents);
            }

            List<Identifier> customIds = List.of(
                    new Identifier("golditemexpansion", "god_positive_status_effect"),
                    new Identifier("golditemexpansion", "god_negative_status_effect")
            );

            List<CompletableFuture<SpriteContents>> loadFutures = new ArrayList<>();

            // 2. 加载自定义贴图，打印加载状态
            for (Identifier id : customIds) {
                loadFutures.add(CompletableFuture.supplyAsync(() -> {
                    SpriteContents loaded = loadSprite(resourceManager, id);
                    System.out.println("[GoldItemExpansion] 加载自定义贴图: " + (loaded == null ? "null" : loaded.getId()));
                    return loaded;
                }, executor));
            }

            return CompletableFuture.allOf(loadFutures.toArray(new CompletableFuture[0]))
                    .thenApply(v -> {
                        // 3. 加载完毕后加入拼接列表并打印
                        for (CompletableFuture<SpriteContents> future : loadFutures) {
                            SpriteContents contents = future.join();
                            if (contents != null) {
                                System.out.println("[GoldItemExpansion] 添加到拼接列表的自定义贴图: " + contents.getId());
                                contentsList.add(contents);
                            }
                        }

                        System.out.println("[GoldItemExpansion] 拼接前列表总数量: " + contentsList.size());
                        for (SpriteContents sc : contentsList) {
                            System.out.println("[GoldItemExpansion] 列表项: " + (sc == null ? "null" : sc.getId()));
                        }

                        SpriteLoader self = (SpriteLoader) (Object) this;
                        StitchResult stitched = self.stitch(contentsList, mipLevel, executor);

                        // 4. 拼接完成后打印拼接图集内容
                        System.out.println("[GoldItemExpansion] 拼接后图集内容:");
                        stitched.regions().forEach((id, sprite) ->
                                System.out.println(" - " + id + " 在位置 x=" + sprite.getX() + ", y=" + sprite.getY()));

                        return stitched;
                    });
        });

        cir.setReturnValue(newFuture);
    }

    @Unique
    private SpriteContents loadSprite(ResourceManager manager, Identifier id) {
        try {
            System.out.println("[GoldItemExpansion] 🔍 尝试加载贴图: " + id);
            Identifier texturePath = new Identifier(id.getNamespace(), "textures/mob_effects/" + id.getPath() + ".png");

            var optionalResource = manager.getResource(texturePath);
            if (optionalResource.isEmpty()) {
                System.out.println("[GoldItemExpansion] ❌ 资源未找到: " + texturePath);
                return null;
            }

            Resource resource = optionalResource.get();
            SpriteContents contents = SpriteLoader.load(id, resource);

            if (contents == null) {
                System.out.println("[GoldItemExpansion] ❌ 加载贴图失败: " + id);
            } else {
                System.out.println("[GoldItemExpansion] ✅ 成功加载贴图: " + id);
            }

            return contents;

        } catch (Exception e) {
            System.out.println("[GoldItemExpansion] ❌ 异常加载贴图 " + id + ": " + e.getMessage());
            LOGGER.error("Failed to load custom sprite: {}", id, e);
            return null;
        }
    }
}