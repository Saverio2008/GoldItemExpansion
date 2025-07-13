package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.saverio.golditemexpansion.util.GodEffectApplier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GodPositiveStatusEffect extends StatusEffect implements GodEffectApplier {
    public static final LinkedHashMap<StatusEffect, Integer> GOD_POSITIVE_EFFECTS = new LinkedHashMap<>() {{
        put(StatusEffects.SPEED, 4);
        put(StatusEffects.HASTE, 4);
        put(StatusEffects.STRENGTH, 4);
        put(StatusEffects.JUMP_BOOST, 4);
        put(StatusEffects.REGENERATION, 4);
        put(StatusEffects.RESISTANCE, 4);
        put(StatusEffects.FIRE_RESISTANCE, 0);
        put(StatusEffects.WATER_BREATHING, 0);
        put(StatusEffects.NIGHT_VISION, 0);
        put(StatusEffects.HEALTH_BOOST, 4);
        put(StatusEffects.ABSORPTION, 4);
        put(StatusEffects.SATURATION, 4);
        put(StatusEffects.LUCK, 4);
        put(StatusEffects.CONDUIT_POWER, 0);
        put(StatusEffects.DOLPHINS_GRACE, 4);
        put(StatusEffects.HERO_OF_THE_VILLAGE, 4);
    }};

    public GodPositiveStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFF77FF);
    }

    @Override
    public Map<StatusEffect, Integer> getGodEffects() {
        return GOD_POSITIVE_EFFECTS;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.getWorld().isClient || entity.getServer() == null) return;
        entity.getServer().execute(() -> {
            entity.removeStatusEffect(ModEffects.GOD_NEGATIVE_EFFECT);
            StatusEffectInstance instance = entity.getStatusEffect(this);
            if (instance == null) return;
            int duration = instance.getDuration();
            applyGodSubEffects(entity, duration);
        });
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.getWorld().isClient) return;
        Objects.requireNonNull(entity.getServer()).execute(() -> removeGodSubEffects(entity));
    }
}