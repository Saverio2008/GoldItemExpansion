package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.client.MinecraftClient;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void skipGodStatusEffect(DrawContext context, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        Collection<StatusEffectInstance> effects = player.getStatusEffects();
        for (StatusEffectInstance effectInstance : effects) {
            if (effectInstance.getEffectType() == ModEffects.GOD_STATUS_EFFECT) {
                ci.cancel();
                return;
            }
        }
    }
}
