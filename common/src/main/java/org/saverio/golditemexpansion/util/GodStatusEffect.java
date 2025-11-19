package org.saverio.golditemexpansion.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Holder;
import org.saverio.golditemexpansion.effect.ModEffects;

import java.util.function.Predicate;

public abstract class GodStatusEffect extends MobEffect {
    private static final Holder<MobEffect> POSITIVE = ModEffects.godPositiveHolder();
    private static final Holder<MobEffect> NEGATIVE = ModEffects.godNegativeHolder();
    private final int baseDuration;
    private final Predicate<LivingEntity> isPositive;
    protected GodStatusEffect(MobEffectCategory category, int color,
                              int baseDuration,
                              Predicate<LivingEntity> isPositive) {
        super(category, color);
        this.baseDuration = baseDuration;
        this.isPositive = isPositive;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide) return;
        boolean positive = isPositive.test(entity);
        Holder<MobEffect> childEffect = positive ? POSITIVE : NEGATIVE;
        MobEffectInstance existing = entity.getEffect(childEffect);
        int totalDuration = baseDuration;
        if (existing != null) totalDuration += existing.getDuration();
        entity.removeEffect(childEffect);
        entity.addEffect(new MobEffectInstance(childEffect, totalDuration, 0,
                false, true, true));
    }
}