package org.saverio.golditemexpansion.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.LinkedHashMap;
import java.util.Map;

public class GodEffects {
    // 正面效果
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
    // 负面效果
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

    public static void applyGodEffects(LivingEntity entity, int duration, boolean positive) {
        Map<StatusEffect, Integer> effects = positive ? GOD_POSITIVE_EFFECTS : GOD_NEGATIVE_EFFECTS;
        for (Map.Entry<StatusEffect, Integer> entry : effects.entrySet()) {
            StatusEffect effect = entry.getKey();
            int amplifier = entry.getValue();
            replaceEffect(entity, effect, duration, amplifier);
        }
    }

    private static void replaceEffect(LivingEntity entity, StatusEffect effect, int duration, int amplifier) {
        StatusEffectInstance current = entity.getStatusEffect(effect);
        if (current == null || current.getDuration() < duration - 20) {
            entity.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, false, false));
        }
    }
}
