package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;

public class GodStatusEffect extends StatusEffect {
    public GodStatusEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xFFFF69B4);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @SuppressWarnings("ReassignedVariable")
    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.getWorld().isClient) return;
        StatusEffectInstance current = entity.getStatusEffect(this);
        if (current == null) return;
        int duration = current.getDuration();
        boolean isPositive;
        switch (amplifier) {
            case 0 -> isPositive = entity instanceof PlayerEntity;
            case 1 -> isPositive = entity.getGroup() == EntityGroup.UNDEAD;
            case 2 -> isPositive = entity.getGroup() == EntityGroup.ARTHROPOD;
            default -> isPositive = true;
        }
        StatusEffect childEffect = isPositive ? ModEffects.GOD_POSITIVE_EFFECT : ModEffects.GOD_NEGATIVE_EFFECT;
        StatusEffectInstance child = entity.getStatusEffect(childEffect);
        int newDuration = duration;
        if (child != null) {
            newDuration += child.getDuration();
        }
        entity.addStatusEffect(new StatusEffectInstance(childEffect, newDuration, 0, true, true));
    }
}