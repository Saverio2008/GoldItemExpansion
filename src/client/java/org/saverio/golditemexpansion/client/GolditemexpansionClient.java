package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import org.saverio.golditemexpansion.effect.ModEffects;

public class GolditemexpansionClient implements ClientModInitializer {

    private static boolean hasRegisteredIcons = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!hasRegisteredIcons && client.world != null) {
                hasRegisteredIcons = true;

                StatusEffectSpriteManager spriteManager = MinecraftClient.getInstance().getStatusEffectSpriteManager();

                // 强制加载你的自定义药水图标
                spriteManager.getSprite(ModEffects.GOD_POSITIVE_EFFECT);
                spriteManager.getSprite(ModEffects.GOD_NEGATIVE_EFFECT);

                System.out.println("[Golditemexpansion] ✅ 自定义药水图标注册成功！");
            }
        });
    }
}