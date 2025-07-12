package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Unique
    private static final int MAX_LOGS = 5;
    @Unique
    private static int logCount = 0;
    @Unique
    private static final Set<StatusEffect> loggedEffects = new HashSet<>();

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"))
    private void beforeRenderStatusEffects(CallbackInfo ci) {
        if (logCount >= MAX_LOGS) return;

        MinecraftClient client = MinecraftClient.getInstance();
        StatusEffectSpriteManager manager = client.getStatusEffectSpriteManager();

        if (client.player != null) {
            client.player.getStatusEffects().forEach(effectInstance -> {
                StatusEffect effect = effectInstance.getEffectType();

                // 只打印自定义的效果，或者你可以换成你想过滤的条件
                if (!isCustomEffect(effect)) return;

                // 同一个效果只打印一次
                if (loggedEffects.contains(effect)) return;

                Sprite sprite = manager.getSprite(effect);

                if (sprite == null) {
                    System.out.println("[GoldItemExpansion][HUD] 状态效果 " + effect + " 的Sprite为null");
                } else if (sprite.getContents() == null) {
                    System.out.println("[GoldItemExpansion][HUD] 状态效果 " + effect + " 的Sprite内容为空");
                } else {
                    System.out.println("[GoldItemExpansion][HUD] 状态效果 " + effect + " 使用贴图：" + sprite.getAtlasId());
                }
                assert sprite != null;
                System.out.println("[GoldItemExpansion][HUD] UV坐标: minU=" + sprite.getMinU() + ", maxU=" + sprite.getMaxU() +
                        ", minV=" + sprite.getMinV() + ", maxV=" + sprite.getMaxV());
                System.out.println("Effect ID: " + Registries.STATUS_EFFECT.getId(effect));
                loggedEffects.add(effect);
                logCount++;
            });
        }
    }
    @Unique
    private boolean isCustomEffect(StatusEffect effect) {
        return effect == ModEffects.GOD_POSITIVE_EFFECT || effect == ModEffects.GOD_NEGATIVE_EFFECT;
    }
}
