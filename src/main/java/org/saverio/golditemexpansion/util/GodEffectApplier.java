package org.saverio.golditemexpansion.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Map;
import java.util.Objects;

public interface GodEffectApplier {
    Map<StatusEffect, Integer> getGodEffects();
    default void applyGodSubEffects(LivingEntity entity, int duration) {
        for (Map.Entry<StatusEffect, Integer> entry : getGodEffects().entrySet()) {
            StatusEffect effect = entry.getKey();
            int amplifier = entry.getValue();
            entity.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, false, false,false));
        }
    }
    @SuppressWarnings("ReassignedVariable")
    default void removeGodSubEffects(LivingEntity entity) {
        var server = Objects.requireNonNull(entity.getServer());
        server.execute(() -> {
            // 延迟 5 tick 执行，确保避开状态遍历
            for (int i = 0; i < 5; i++) {
                server.execute(() -> {
                });
            }
            server.execute(() -> {
                var currentEffects = new java.util.ArrayList<>(entity.getStatusEffects());
                for (StatusEffect effect : getGodEffects().keySet()) {
                    boolean hasEffect = false;
                    for (StatusEffectInstance inst : currentEffects) {
                        if (inst.getEffectType() == effect) {
                            hasEffect = true;
                            break;
                        }
                    }
                    if (hasEffect) entity.removeStatusEffect(effect);
                }
            });
        });
    }
}