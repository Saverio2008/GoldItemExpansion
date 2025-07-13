package org.saverio.golditemexpansion.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Map;

public interface GodEffectApplier {
    Map<StatusEffect, Integer> getGodEffects();
    default void applyGodSubEffects(LivingEntity entity) {
        for (Map.Entry<StatusEffect, Integer> entry : getGodEffects().entrySet()) {
            StatusEffect effect = entry.getKey();
            int amplifier = entry.getValue();
            entity.addStatusEffect(new StatusEffectInstance(effect, -1, amplifier, false, false, false));
        }
    }
    default void removeGodSubEffects(LivingEntity entity) {
        TickDelayExecutor.runLater(entity.getServer(), 5, () -> {
            for (StatusEffect effect : getGodEffects().keySet()) {
                if (entity.hasStatusEffect(effect)) {
                    entity.removeStatusEffect(effect);
                }
            }
        });
    }
}