package org.saverio.golditemexpansion.util;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public interface GodEffectApplier {

    Map<MobEffect, Integer> getGodEffects();

    default void applyGodSubEffects(LivingEntity entity, MobEffectInstance instance) {
        for (Map.Entry<MobEffect, Integer> entry : getGodEffects().entrySet()) {
            MobEffect effect = entry.getKey();
            int duration = instance.getDuration();
            int amplifier = instance.getAmplifier();
            entity.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false, false));
        }
    }
}