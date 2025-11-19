package org.saverio.golditemexpansion.effect;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.saverio.golditemexpansion.util.GodEffectApplier;

import java.util.LinkedHashMap;
import java.util.Map;

public final class GodNegativeStatusEffect extends MobEffect implements GodEffectApplier {

    public static final LinkedHashMap<Holder<MobEffect>, Integer> GOD_NEGATIVE_EFFECTS = new LinkedHashMap<>() {{
        put(MobEffects.WEAKNESS, 4);
        put(MobEffects.MOVEMENT_SLOWDOWN, 4);
        put(MobEffects.DIG_SLOWDOWN, 4);
        put(MobEffects.UNLUCK, 4);
        put(MobEffects.CONFUSION, 4);
        put(MobEffects.BLINDNESS, 4);
        put(MobEffects.HUNGER, 4);
        put(MobEffects.DARKNESS, 4);
        put(MobEffects.BAD_OMEN, 4);
        put(MobEffects.SLOW_FALLING, 4);
    }};

    public GodNegativeStatusEffect() {
        super(MobEffectCategory.HARMFUL, 0xAA222222);
    }

    @Override
    public Map<Holder<MobEffect>, Integer> getGodEffects() {
        return GOD_NEGATIVE_EFFECTS;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int i) {
        if (entity.level().isClientSide) return;
        MobEffectInstance self = entity.getEffect(ModEffects.godNegativeHolder());
        if (self == null) return;
        entity.removeEffect(ModEffects.godPositiveHolder());
        applyGodSubEffects(entity, self.getDuration());
    }
}