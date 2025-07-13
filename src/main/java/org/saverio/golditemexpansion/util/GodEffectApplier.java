package org.saverio.golditemexpansion.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.*;

public interface GodEffectApplier {
    Map<StatusEffect, Integer> getGodEffects();

    default void applyGodSubEffects(LivingEntity entity, int duration) {
        StatusEffectInstance hiddenChain = buildEffectChain(duration);
        if (hiddenChain != null) {
            entity.addStatusEffect(hiddenChain);
        }
    }

    @SuppressWarnings("ReassignedVariable")
    default StatusEffectInstance buildEffectChain(int duration) {
        List<Map.Entry<StatusEffect, Integer>> entries = new ArrayList<>(getGodEffects().entrySet());
        Collections.reverse(entries); // 使链式顺序正确（外 -> 内）

        StatusEffectInstance chain = null;
        for (Map.Entry<StatusEffect, Integer> entry : entries) {
            StatusEffect effect = entry.getKey();
            int amplifier = entry.getValue();

            chain = new StatusEffectInstance(
                    effect,
                    duration,
                    amplifier,
                    false,
                    false,
                    false,
                    chain,
                    effect.getFactorCalculationDataSupplier()
            );
        }

        return chain;
    }
}