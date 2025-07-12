package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.saverio.golditemexpansion.client.mixin.accessor.SpriteAtlasHolderAccessor;
import org.saverio.golditemexpansion.effect.ModEffects;

public class GolditemexpansionClient implements ClientModInitializer {

    private boolean hasRegisteredIcons = false;

    @SuppressWarnings("resource")
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!hasRegisteredIcons) {
                hasRegisteredIcons = true;
                client.execute(() -> {
                    StatusEffectSpriteManager spriteManager = client.getStatusEffectSpriteManager();
                    if (spriteManager != null) {
                        // 清理旧缓存
                        SpriteAtlasTexture atlas = ((SpriteAtlasHolderAccessor) spriteManager).golditemexpansion$getAtlas();
                        if (atlas != null) {
                            atlas.clear();
                            System.out.println("[Golditemexpansion] 🧹 清除 StatusEffectSpriteManager 图集缓存！");
                        }

                        // 触发资源重新加载，确保图集被重新加载
                        MinecraftClient.getInstance().reloadResources().thenRun(() -> {
                            System.out.println("[Golditemexpansion] 🔄 资源重新加载完成");

                            // 重新请求你的自定义药水图标，这时贴图应该已经加载了
                            spriteManager.getSprite(ModEffects.GOD_POSITIVE_EFFECT);
                            spriteManager.getSprite(ModEffects.GOD_NEGATIVE_EFFECT);
                            System.out.println("[Golditemexpansion] ✅ 自定义药水图标注册成功！");
                        });
                    }
                });
            }
        });
    }
}