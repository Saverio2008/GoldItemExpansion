package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.saverio.golditemexpansion.util.GodEffectApplier;

import java.util.LinkedHashMap;
import java.util.Map;

public class GodNegativeStatusEffect extends StatusEffect implements GodEffectApplier {
    public static final LinkedHashMap<StatusEffect, Integer> GOD_NEGATIVE_EFFECTS = new LinkedHashMap<>() {{
        put(StatusEffects.WEAKNESS, 4);
        put(StatusEffects.SLOWNESS, 4);
        put(StatusEffects.MINING_FATIGUE, 4);
        put(StatusEffects.UNLUCK, 4);
        put(StatusEffects.NAUSEA, 4);
        put(StatusEffects.BLINDNESS, 4);
        put(StatusEffects.HUNGER, 4);
        put(StatusEffects.DARKNESS, 4);
        put(StatusEffects.BAD_OMEN, 4);
        put(StatusEffects.POISON, 4);
        put(StatusEffects.WITHER, 4);
        put(StatusEffects.SLOW_FALLING, 4);
    }};

    public GodNegativeStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xAA0000);
    }

    @Override
    public Map<StatusEffect, Integer> getGodEffects() {
        return GOD_NEGATIVE_EFFECTS;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity.getWorld().isClient) return;
        StatusEffectInstance instance = entity.getStatusEffect(this);
        if (instance == null) return;
        int duration = instance.getDuration();
        applyGodSubEffects(entity, duration);
    }
}