package org.saverio.golditemexpansion.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.saverio.golditemexpansion.effect.GodPositiveStatusEffect;
import org.saverio.golditemexpansion.effect.GodNegativeStatusEffect;
import org.saverio.golditemexpansion.util.GodEffectRemoveSkipManager;

public final class EffectChangeListenerManager {
    public static void onEffectAdded(LivingEntity entity, MobEffectInstance effect, @Nullable Entity ignoredSource) {
        var mobEffect = effect.getEffect();
        if (mobEffect instanceof GodPositiveStatusEffect godPositive) {
            godPositive.onEffectApplied(entity);
        } else if (mobEffect instanceof GodNegativeStatusEffect godNegative) {
            godNegative.onEffectApplied(entity);
        }
    }

    public static void onEffectRemoved(LivingEntity entity, MobEffectInstance effect) {
        var mobEffect = effect.getEffect();
        if (GodEffectRemoveSkipManager.shouldSkip(entity)) return;
        if (mobEffect instanceof GodPositiveStatusEffect godPositive) {
            godPositive.onEffectRemoved(entity);
        } else if (mobEffect instanceof GodNegativeStatusEffect godNegative) {
            godNegative.onEffectRemoved(entity);
        }
    }
}