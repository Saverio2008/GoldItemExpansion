package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import org.saverio.golditemexpansion.util.EffectUtils;

import java.util.Objects;

public class GodStatusEffect extends StatusEffect {
    public GodStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFF77FF);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.getWorld().isClient && entity.age % 20 == 0) {
            int duration = Objects.requireNonNull(entity.getStatusEffect(this)).getDuration();

            if (entity instanceof PlayerEntity) {
                // 玩家：覆盖所有正面效果，不显示粒子和图标，且只在剩余时间低时刷新避免频繁移除添加
                replaceEffect(entity, StatusEffects.SPEED, duration, 4);
                replaceEffect(entity, StatusEffects.HASTE, duration, 4);
                replaceEffect(entity, StatusEffects.STRENGTH, duration, 4);
                replaceEffect(entity, StatusEffects.JUMP_BOOST, duration, 4);
                replaceEffect(entity, StatusEffects.REGENERATION, duration, 4);
                replaceEffect(entity, StatusEffects.RESISTANCE, duration, 4);
                replaceEffect(entity, StatusEffects.FIRE_RESISTANCE, duration, 0);
                replaceEffect(entity, StatusEffects.WATER_BREATHING, duration, 0);
                replaceEffect(entity, StatusEffects.INVISIBILITY, duration, 0);
                replaceEffect(entity, StatusEffects.NIGHT_VISION, duration, 0);
                replaceEffect(entity, StatusEffects.HEALTH_BOOST, duration, 4);
                replaceEffect(entity, StatusEffects.ABSORPTION, duration, 4);
                replaceEffect(entity, StatusEffects.LUCK, duration, 4);
                replaceEffect(entity, StatusEffects.SLOW_FALLING, duration, 0);
                replaceEffect(entity, StatusEffects.CONDUIT_POWER, duration, 0);
                replaceEffect(entity, StatusEffects.DOLPHINS_GRACE, duration, 4);
                replaceEffect(entity, StatusEffects.HERO_OF_THE_VILLAGE, duration, 4);
            } else {
                // 非玩家：覆盖所有原版负面效果，不显示粒子和图标，避免频繁刷新
                replaceEffect(entity, StatusEffects.WEAKNESS, duration, 4);
                replaceEffect(entity, StatusEffects.SLOWNESS, duration, 4);
                replaceEffect(entity, StatusEffects.MINING_FATIGUE, duration, 4);
                replaceEffect(entity, StatusEffects.UNLUCK, duration, 4);
                replaceEffect(entity, StatusEffects.NAUSEA, duration, 4);
                replaceEffect(entity, StatusEffects.BLINDNESS, duration, 4);
                replaceEffect(entity, StatusEffects.HUNGER, duration, 4);
                replaceEffect(entity, StatusEffects.DARKNESS, duration, 4);
                replaceEffect(entity, StatusEffects.BAD_OMEN, duration, 4);
            }
        }
    }

    private void replaceEffect(LivingEntity entity, StatusEffect effect, int duration, int amplifier) {
        StatusEffectInstance current = entity.getStatusEffect(effect);
        if (current == null || current.getDuration() < duration - 20) {
            StatusEffectInstance instance = new StatusEffectInstance(effect, duration, amplifier, false, false);
            EffectUtils.setHideIcon(instance, true);  // 标记为隐藏图标
            entity.addStatusEffect(instance);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}