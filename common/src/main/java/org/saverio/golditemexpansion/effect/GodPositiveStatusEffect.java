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

public final class GodPositiveStatusEffect extends MobEffect implements GodEffectApplier {
    public static final LinkedHashMap<Holder<MobEffect>, Integer> GOD_POSITIVE_EFFECTS = new LinkedHashMap<>() {{
        put(MobEffects.MOVEMENT_SPEED, 4);
        put(MobEffects.DIG_SPEED, 4);
        put(MobEffects.DAMAGE_BOOST, 4);
        put(MobEffects.JUMP, 4);
        put(MobEffects.REGENERATION, 4);
        put(MobEffects.DAMAGE_RESISTANCE, 4);
        put(MobEffects.FIRE_RESISTANCE, 0);
        put(MobEffects.WATER_BREATHING, 0);
        put(MobEffects.NIGHT_VISION, 0);
        put(MobEffects.HEALTH_BOOST, 4);
        put(MobEffects.ABSORPTION, 4);
        put(MobEffects.SATURATION, 4);
        put(MobEffects.LUCK, 4);
        put(MobEffects.CONDUIT_POWER, 0);
        put(MobEffects.DOLPHINS_GRACE, 4);
    }};

    public GodPositiveStatusEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF77FF);
    }

    @Override
    public Map<Holder<MobEffect>, Integer> getGodEffects() {
        return GOD_POSITIVE_EFFECTS;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int i) {
        if (entity.level().isClientSide) return;
        MobEffectInstance self = entity.getEffect(ModEffects.godPositiveHolder());
        if (self == null) return;
        entity.removeEffect(ModEffects.godNegativeHolder());
        applyGodSubEffects(entity, self.getDuration());
    }
}