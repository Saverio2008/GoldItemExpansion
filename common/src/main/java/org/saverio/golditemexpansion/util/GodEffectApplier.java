package org.saverio.golditemexpansion.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface GodEffectApplier {

    Map<MobEffect, Integer> getGodEffects();

    default void applyGodSubEffects(LivingEntity entity) {
        for (Map.Entry<MobEffect, Integer> entry : getGodEffects().entrySet()) {
            MobEffect effect = entry.getKey();
            int amplifier = entry.getValue();
            MobEffectInstance existing = entity.getEffect(effect);
            if (existing != null && existing.getDuration() == -1) {
                continue;
            }
            entity.addEffect(new MobEffectInstance(effect, -1, amplifier, false, false, false));
        }
    }

    default void removeGodSubEffects(LivingEntity entity) {
        if (entity.level().isClientSide()) return;
        List<MobEffect> effectsToRemove = new ArrayList<>(getGodEffects().keySet());
        for (MobEffect effect : effectsToRemove) {
            entity.removeEffect(effect);
        }
    }
}