package org.saverio.golditemexpansion.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.*;

public interface GodEffectApplier {
    Map<StatusEffect, Integer> getGodEffects();
    default void applyGodSubEffects(LivingEntity entity, int duration) {
        StatusEffectInstance hiddenChain = buildEffectChain(duration);
        for (Map.Entry<StatusEffect, Integer> entry : getGodEffects().entrySet()) {
            StatusEffect effect = entry.getKey();
            int amplifier = entry.getValue();

            Optional<StatusEffectInstance.FactorCalculationData> factorData = effect.getFactorCalculationDataSupplier();

            StatusEffectInstance newEffectInstance = new StatusEffectInstance(
                    effect,
                    duration,
                    amplifier,
                    false,
                    false,
                    false,
                    hiddenChain,
                    factorData
            );

            entity.addStatusEffect(newEffectInstance);
        }
    }
    default StatusEffectInstance buildEffectChain(int duration) {
        List<Map.Entry<StatusEffect, Integer>> entries = new ArrayList<>(getGodEffects().entrySet());
        Collections.reverse(entries);

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