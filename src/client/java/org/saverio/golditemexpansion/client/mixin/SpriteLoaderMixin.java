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

import static org.apache.logging.log4j.LogManager.getLogger;
import org.apache.logging.log4j.Logger;

@Mixin(SpriteLoader.class)
public class SpriteLoaderMixin {

    @Unique
    private static final Logger LOGGER = getLogger("GoldItemExpansion");

    @Inject(
            method = "load(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/Identifier;ILjava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void onLoad(ResourceManager resourceManager, Identifier atlasId, int mipLevel, Executor executor,
                        CallbackInfoReturnable<CompletableFuture<SpriteLoader.StitchResult>> cir) {
        // 只拦截加载 mob_effects 图集
        if (!atlasId.equals(new Identifier("minecraft", "mob_effects"))) {
            return;
        }

        LOGGER.info("[GoldItemExpansion] Injecting custom mob effect icons...");

        CompletableFuture<SpriteLoader.StitchResult> originalFuture = cir.getReturnValue();

        CompletableFuture<SpriteLoader.StitchResult> newFuture = originalFuture.thenCompose(originalResult -> {
            try {
                // 取出原有贴图ID集合
                Set<Identifier> originalIds = originalResult.regions().keySet();
                LOGGER.info("[GoldItemExpansion] Original sprite count: {}", originalIds.size());

                List<CompletableFuture<SpriteContents>> loadFutures = new ArrayList<>();
                // 保存所有ID，方便后续对应打印失败日志
                List<Identifier> allIds = new ArrayList<>(originalIds);

                // 异步加载所有原始贴图
                for (Identifier id : originalIds) {
                    loadFutures.add(CompletableFuture.supplyAsync(() -> loadSpriteContents(resourceManager, id), executor));
                }

                // 自定义注入的图标ID列表
                List<Identifier> customIds = List.of(
                        new Identifier("golditemexpansion", "mob_effects/god_positive_status_effect"),
                        new Identifier("golditemexpansion", "mob_effects/god_negative_status_effect")
                );
                allIds.addAll(customIds);

                // 异步加载自定义贴图
                for (Identifier customId : customIds) {
                    LOGGER.info("[GoldItemExpansion] Attempting to load custom icon: {}", customId);
                    loadFutures.add(CompletableFuture.supplyAsync(() -> loadSpriteContents(resourceManager, customId), executor));
                }

                // 等待所有加载完毕，合并返回新的StitchResult
                return CompletableFuture.allOf(loadFutures.toArray(new CompletableFuture[0]))
                        .thenApply(v -> {
                            List<SpriteContents> allContents = new ArrayList<>();
                            for (int i = 0; i < loadFutures.size(); i++) {
                                SpriteContents contents = loadFutures.get(i).join();
                                if (contents != null) {
                                    LOGGER.info("[GoldItemExpansion] Sprite loaded: {}", contents.getId());
                                    allContents.add(contents);
                                } else {
                                    Identifier failedId = allIds.get(i);
                                    LOGGER.error("[GoldItemExpansion] ❌ Failed to load sprite for: {}", failedId);
                                }
                            }

                            SpriteLoader self = (SpriteLoader)(Object)this;
                            LOGGER.info("[GoldItemExpansion] Final stitching {} sprites...", allContents.size());
                            return self.stitch(allContents, mipLevel, executor);
                        });

            } catch (Exception e) {
                LOGGER.error("[GoldItemExpansion] Failed to reload sprites with custom additions", e);
                return CompletableFuture.completedFuture(originalResult);
            }
        });

        // 用新Future替换返回值
        cir.setReturnValue(newFuture);
    }

    @Unique
    private SpriteContents loadSpriteContents(ResourceManager manager, Identifier id) {
        try {
            Identifier resourceId = idToResourceId(id);
            Optional<Resource> optionalResource = manager.getResource(resourceId);
            if (optionalResource.isEmpty()) {
                LOGGER.error("[GoldItemExpansion] ❌ Texture not found: {}", resourceId);
                return null;
            }

            Resource resource = optionalResource.get();

            AnimationResourceMetadata animationMetadata;
            try {
                animationMetadata = resource.getMetadata().decode(AnimationResourceMetadata.READER).orElse(AnimationResourceMetadata.EMPTY);
            } catch (Exception e) {
                LOGGER.error("[GoldItemExpansion] ❌ Metadata error for {}", id, e);
                return null;
            }

            NativeImage image;
            try (InputStream input = resource.getInputStream()) {
                image = NativeImage.read(input);
            }

            SpriteDimensions dimensions = animationMetadata.getSize(image.getWidth(), image.getHeight());

            // 校验尺寸合法性
            if (image.getWidth() % dimensions.width() != 0 || image.getHeight() % dimensions.height() != 0) {
                LOGGER.error("[GoldItemExpansion] ❌ Image {} size {},{} is not multiple of frame size {},{}", id, image.getWidth(), image.getHeight(), dimensions.width(), dimensions.height());
                image.close();
                return null;
            }

            return new SpriteContents(id, dimensions, image, animationMetadata);
        } catch (Exception e) {
            LOGGER.error("[GoldItemExpansion] ❌ Failed to load sprite contents for {}", id, e);
            return null;
        }
    }

    @Unique
    private Identifier idToResourceId(Identifier id) {
        // 资源路径转换：mob_effects/god_positive_status_effect -> textures/mob_effects/god_positive_status_effect.png
        return new Identifier(id.getNamespace(), "textures/" + id.getPath() + ".png");
    }
}