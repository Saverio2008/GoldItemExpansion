package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Collection;
import java.util.stream.Collectors;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @ModifyVariable(method = "renderStatusEffectOverlay", at = @At("STORE"), ordinal = 0)
    private Collection<StatusEffectInstance> filterGodStatusEffect(Collection<StatusEffectInstance> original) {
        return original.stream()
                .filter(e -> e.getEffectType() != ModEffects.GOD_STATUS_EFFECT)
                .collect(Collectors.toList());
    }
}
