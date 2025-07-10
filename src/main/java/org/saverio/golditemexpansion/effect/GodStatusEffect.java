package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.saverio.golditemexpansion.util.LivingEntityUtils;

public class GodStatusEffect extends StatusEffect {
    public GodStatusEffect() {
        super(StatusEffectCategory.NEUTRAL, 0x00000000); // 无颜色
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @SuppressWarnings("ReassignedVariable")
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getWorld().isClient) return;

        StatusEffectInstance godInstance = entity.getStatusEffect(this);
        if (godInstance == null) return;

        int duration = godInstance.getDuration();
        boolean isPositive;

        // 根据 amplifier 决定使用哪个子效果
        switch (amplifier) {
            case 0 -> isPositive = entity instanceof PlayerEntity;
            case 1 -> isPositive = entity.getGroup() == EntityGroup.UNDEAD;
            case 2 -> isPositive = entity.getGroup() == EntityGroup.ARTHROPOD;
            default -> isPositive = true;
        }

        StatusEffect childEffect = isPositive
                ? ModEffects.GOD_POSITIVE_EFFECT
                : ModEffects.GOD_NEGATIVE_EFFECT;
        StatusEffectInstance currentChild = entity.getStatusEffect(childEffect);
        int newDuration = duration;

        if (currentChild != null) {
            newDuration += currentChild.getDuration();
        }
        entity.addStatusEffect(new StatusEffectInstance(
                childEffect,
                newDuration,
                0,
                false,
                false
        ));

        LivingEntityUtils.setGodStatusApplied(entity, true);
    }
}