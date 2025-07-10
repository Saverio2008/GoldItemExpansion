package org.saverio.golditemexpansion.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Map;

public interface GodEffectApplier {
    Map<StatusEffect, Integer> getGodEffects();

    default void applyGodSubEffects(LivingEntity entity, int duration) {
        for (Map.Entry<StatusEffect, Integer> entry : getGodEffects().entrySet()) {
            StatusEffect effect = entry.getKey();
            int amplifier = entry.getValue();
            StatusEffectInstance current = entity.getStatusEffect(effect);
            if (current == null || current.getDuration() < duration - 20) {
                entity.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, false, false));
            }
        }
    }
}