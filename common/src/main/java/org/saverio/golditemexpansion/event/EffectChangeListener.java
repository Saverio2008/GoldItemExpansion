package org.saverio.golditemexpansion.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface EffectChangeListener {
    void onEffectAdded(LivingEntity entity, MobEffectInstance effect, @Nullable Entity source);
    void onEffectRemoved(LivingEntity entity, MobEffectInstance effect);
}
