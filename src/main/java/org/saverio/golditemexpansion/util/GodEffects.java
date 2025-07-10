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
        put(StatusEffects.SPEED, 4);             // 迅捷
        put(StatusEffects.HASTE, 4);             // 急迫
        put(StatusEffects.STRENGTH, 4);          // 力量
        put(StatusEffects.JUMP_BOOST, 4);        // 跳跃提升
        put(StatusEffects.REGENERATION, 4);      // 生命恢复
        put(StatusEffects.RESISTANCE, 4);        // 抗性提升
        put(StatusEffects.FIRE_RESISTANCE, 0);   // 抗火
        put(StatusEffects.WATER_BREATHING, 0);   // 水下呼吸
        put(StatusEffects.NIGHT_VISION, 0);      // 夜视
        put(StatusEffects.HEALTH_BOOST, 4);      // 生命提升
        put(StatusEffects.ABSORPTION, 4);        // 伤害吸收
        put(StatusEffects.SATURATION, 4);        // 饱和
        put(StatusEffects.LUCK, 4);              // 幸运
        put(StatusEffects.CONDUIT_POWER, 0);     // 潮涌能量
        put(StatusEffects.DOLPHINS_GRACE, 4);    // 海豚的恩惠
        put(StatusEffects.HERO_OF_THE_VILLAGE, 4);// 村庄英雄
    }};
    // 负面效果
    public static final LinkedHashMap<StatusEffect, Integer> GOD_NEGATIVE_EFFECTS = new LinkedHashMap<>() {{
        put(StatusEffects.WEAKNESS, 4);         // 虚弱
        put(StatusEffects.SLOWNESS, 4);         // 缓慢
        put(StatusEffects.MINING_FATIGUE, 4);   // 挖掘疲劳
        put(StatusEffects.UNLUCK, 4);           // 霉运
        put(StatusEffects.NAUSEA, 4);           // 反胃
        put(StatusEffects.BLINDNESS, 4);        // 失明
        put(StatusEffects.HUNGER, 4);           // 饥饿
        put(StatusEffects.DARKNESS, 4);         // 黑暗
        put(StatusEffects.BAD_OMEN, 4);         // 不祥之兆
        put(StatusEffects.POISON, 4);           // 中毒
        put(StatusEffects.WITHER, 4);           // 凋零
        put(StatusEffects.SLOW_FALLING, 4);     // 缓降
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
