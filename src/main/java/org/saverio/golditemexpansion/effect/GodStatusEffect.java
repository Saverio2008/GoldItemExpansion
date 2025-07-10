package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import org.saverio.golditemexpansion.util.GodEffects;

import java.util.Objects;

public class GodStatusEffect extends StatusEffect {
    public GodStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient && entity.age % 20 == 0) {
            int duration = Objects.requireNonNull(entity.getStatusEffect(this)).getDuration();
            boolean isPositive;
            switch (amplifier) {
                case 0 -> isPositive = entity instanceof PlayerEntity;
                case 1 -> isPositive = entity.getGroup() == EntityGroup.UNDEAD;
                case 2 -> isPositive = entity.getGroup() == EntityGroup.ARTHROPOD;
                default -> isPositive = true;
            }
            GodEffects.applyGodEffects(entity, duration, isPositive);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}