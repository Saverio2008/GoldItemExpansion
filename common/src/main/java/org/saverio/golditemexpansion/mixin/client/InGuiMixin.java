package org.saverio.golditemexpansion.mixin.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.world.effect.MobEffectInstance;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public final class InGuiMixin {
    @Redirect(
            method = "renderEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffectInstance;showIcon()Z"
            )
    )
    private boolean redirectShowIcon(MobEffectInstance instance) {
        if (ModEffects.isGodMainEffect(instance.getEffect())) {
            return false;
        }
        return instance.showIcon();
    }
}
