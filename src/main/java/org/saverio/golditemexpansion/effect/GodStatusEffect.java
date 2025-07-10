package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class GodStatusEffect extends StatusEffect {
    public GodStatusEffect() {
        super(StatusEffectCategory.NEUTRAL, 0x00000000); // 无颜色
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    public void applyChildEffect(LivingEntity entity, int amplifier, int duration) {
        boolean isPositive;
        switch (amplifier) {
            case 0 -> isPositive = entity instanceof PlayerEntity;
            case 1 -> isPositive = entity.getGroup() == EntityGroup.UNDEAD;
            case 2 -> isPositive = entity.getGroup() == EntityGroup.ARTHROPOD;
            default -> isPositive = true;
        }
        StatusEffect childEffect = isPositive
                ? ModEffects.GOD_POSITIVE_EFFECT
                : ModEffects.GOD_NEGATIVE_EFFECT;
        entity.addStatusEffect(new StatusEffectInstance(childEffect, duration, 0, false, false));
    }
}