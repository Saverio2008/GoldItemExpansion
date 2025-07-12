package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import org.saverio.golditemexpansion.effect.ModEffects;

public class GolditemexpansionClient implements ClientModInitializer {

    private boolean hasRegisteredIcons = false;

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!hasRegisteredIcons) {
                hasRegisteredIcons = true;
                client.execute(() -> {
                    StatusEffectSpriteManager spriteManager = client.getStatusEffectSpriteManager();
                    if (spriteManager != null) {
                        spriteManager.getSprite(ModEffects.GOD_POSITIVE_EFFECT);
                        spriteManager.getSprite(ModEffects.GOD_NEGATIVE_EFFECT);
                        System.out.println("[Golditemexpansion] ✅ 进入世界时自定义药水图标注册成功！");
                    }
                });
            }
        });
    }
}