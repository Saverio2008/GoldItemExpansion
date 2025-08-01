package org.saverio.golditemexpansion.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import org.saverio.golditemexpansion.util.GodEffectApplier;

import java.util.LinkedHashMap;
import java.util.Map;

public final class GodPositiveStatusEffect extends MobEffect implements GodEffectApplier {
    public static final LinkedHashMap<MobEffect, Integer> GOD_POSITIVE_EFFECTS = new LinkedHashMap<>() {{
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
    public Map<MobEffect, Integer> getGodEffects() {
        return GOD_POSITIVE_EFFECTS;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }

    public void onEffectApplied(LivingEntity entity) {
        if (entity.level().isClientSide) return;
        if (!(entity.level() instanceof ServerLevel)) return;
        MobEffectInstance instance = entity.getEffect(this);
        if (instance == null) return;
        entity.removeEffect(ModEffectInstances.GOD_NEGATIVE_EFFECT);
        applyGodSubEffects(entity, instance);
    }
}
