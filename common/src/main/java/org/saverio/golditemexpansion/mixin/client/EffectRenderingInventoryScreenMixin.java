package org.saverio.golditemexpansion.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.saverio.golditemexpansion.effect.ModEffectInstances;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

import static org.saverio.golditemexpansion.effect.GodNegativeStatusEffect.GOD_NEGATIVE_EFFECTS;
import static org.saverio.golditemexpansion.effect.GodPositiveStatusEffect.GOD_POSITIVE_EFFECTS;

@Mixin(EffectRenderingInventoryScreen.class)
public final class EffectRenderingInventoryScreenMixin {
    @Inject(method = "renderEffects", at = @At("HEAD"), cancellable = true)
    private void onRenderEffects(GuiGraphics guiGraphics, int i, int j, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        boolean hidePositive = player.hasEffect(ModEffectInstances.GOD_POSITIVE_EFFECT);
        boolean hideNegative = player.hasEffect(ModEffectInstances.GOD_NEGATIVE_EFFECT);

        Collection<MobEffectInstance> filtered = player.getActiveEffects().stream()
                .filter(effectInstance -> {
                    MobEffect effect = effectInstance.getEffect();
                    if (effect.equals(ModEffectInstances.GOD_STATUS_EFFECT) ||
                            effect.equals(ModEffectInstances.GOD_POSITIVE_EFFECT) ||
                            effect.equals(ModEffectInstances.GOD_NEGATIVE_EFFECT)) {
                        return false;
                    } else if (hidePositive && GOD_POSITIVE_EFFECTS.containsKey(effect)) {
                        return false;
                    } else if (hideNegative && GOD_NEGATIVE_EFFECTS.containsKey(effect)) {
                        return false;
                    }
                    return true;
                })
                .toList();

        if (filtered.isEmpty()) {
            ci.cancel();
        }
    }
}
