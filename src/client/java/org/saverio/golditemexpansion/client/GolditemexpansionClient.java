package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
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
                        // 先清除缓存
                        SpriteAtlasTexture atlas = ((SpriteAtlasHolderAccessor) spriteManager).golditemexpansion$getAtlas();
                        if (atlas != null) {
                            atlas.clear();
                            System.out.println("[Golditemexpansion] 🧹 清除 StatusEffectSpriteManager 图集缓存！");
                        }

                        // 再重新请求你的自定义状态效果图标
                        spriteManager.getSprite(ModEffects.GOD_POSITIVE_EFFECT);
                        spriteManager.getSprite(ModEffects.GOD_NEGATIVE_EFFECT);
                        System.out.println("[Golditemexpansion] ✅ 进入世界时自定义药水图标注册成功！");
                    }
                });
            }
        });
    }
}