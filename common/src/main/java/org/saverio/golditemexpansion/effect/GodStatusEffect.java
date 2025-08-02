package org.saverio.golditemexpansion.effect;

import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import static org.saverio.golditemexpansion.effect.ModEffectInstances.GOD_NEGATIVE_EFFECT;
import static org.saverio.golditemexpansion.effect.ModEffectInstances.GOD_POSITIVE_EFFECT;

public final class GodStatusEffect extends MobEffect {
    public GodStatusEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFFF69B4);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {
        if (entity.level().isClientSide) return;
        MobEffectInstance current = entity.getEffect(this);
        if (current == null) return;
        int baseDuration = current.getDuration();
        boolean isPositive;
        switch (amplifier) {
            case 0 -> isPositive = entity instanceof Player;
            case 1 -> isPositive = entity.getMobType() == MobType.UNDEAD;
            case 2 -> isPositive = entity.getMobType() == MobType.ARTHROPOD;
            default -> isPositive = true;
        }
        MobEffect childEffect = isPositive ? GOD_POSITIVE_EFFECT : GOD_NEGATIVE_EFFECT;
        MobEffectInstance existing = entity.getEffect(childEffect);
        int totalDuration = baseDuration;
        if (existing != null) {
            totalDuration += existing.getDuration();
        }
        entity.removeEffect(childEffect);
        entity.addEffect(new MobEffectInstance(
                childEffect,
                totalDuration,
                0,
                false, // ambient
                true,  // visible
                true   // showIcon
        ));
    }
}