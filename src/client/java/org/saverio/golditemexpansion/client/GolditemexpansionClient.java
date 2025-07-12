package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.saverio.golditemexpansion.client.mixin.accessor.SpriteAtlasHolderAccessor;
import org.saverio.golditemexpansion.client.mixin.accessor.SpriteAtlasTextureAccessor;
import org.saverio.golditemexpansion.effect.ModEffects;

import static com.mojang.text2speech.Narrator.LOGGER;

public class GolditemexpansionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier("golditemexpansion", "status_effect_icon_loader");
            }

            @Override
            public CompletableFuture<Void> reload(ResourceReloader.Synchronizer synchronizer,
                                                  ResourceManager manager,
                                                  Profiler prepareProfiler,
                                                  Profiler applyProfiler,
                                                  Executor prepareExecutor,
                                                  Executor applyExecutor) {
                return synchronizer.whenPrepared(Unit.INSTANCE).thenRunAsync(() -> {
                    applyProfiler.startTick();
                    applyProfiler.push("golditemexpansion:status_effect_icon_loader");

                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client != null && client.getStatusEffectSpriteManager() != null) {
                        StatusEffectSpriteManager spriteManager = client.getStatusEffectSpriteManager();
                        try (SpriteAtlasTexture atlas = ((SpriteAtlasHolderAccessor) spriteManager).golditemexpansion$getAtlas()) {

                            if (atlas != null) {
                                Identifier atlasId = ((SpriteAtlasTextureAccessor) atlas).golditemexpansion$getId();
                                Map<Identifier, Sprite> currentSprites = ((SpriteAtlasTextureAccessor) atlas).golditemexpansion$getSprites();

                                System.out.println("[Golditemexpansion] 🗺 当前图集 ID: " + atlasId);
                                System.out.println("[Golditemexpansion] 🧾 当前缓存图标数量: " + currentSprites.size());

                                atlas.clear(); // 清除状态效果图标图集缓存
                                System.out.println("[Golditemexpansion] 🧹 清除图集缓存！");
                            }
                        } catch (Exception e) {
                            LOGGER.error(e.getMessage());
                        }
                        client.reloadResources().thenRun(() -> {
                            StatusEffectSpriteManager sm = client.getStatusEffectSpriteManager();
                            sm.getSprite(ModEffects.GOD_POSITIVE_EFFECT);
                            sm.getSprite(ModEffects.GOD_NEGATIVE_EFFECT);
                            System.out.println("[Golditemexpansion] ✅ 自定义药水图标注册成功！");
                        });
                    }

                    applyProfiler.pop();
                    applyProfiler.endTick();
                }, applyExecutor);
            }
        });
    }
}