package org.saverio.golditemexpansion.util;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Map;

public interface GodEffectApplier {
    Map<Holder<MobEffect>, Integer> getGodEffects();
    default void applyGodSubEffects(LivingEntity entity, int duration) {
        for (Map.Entry<Holder<MobEffect>, Integer> entry : getGodEffects().entrySet()) {
            Holder<MobEffect> effectHolder = entry.getKey();
            int amplifier = entry.getValue();
            entity.removeEffect(effectHolder);
            entity.addEffect(new MobEffectInstance(effectHolder, duration, amplifier, false, false, false));
        }
    }
}