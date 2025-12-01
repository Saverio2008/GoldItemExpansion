package org.saverio.golditemexpansion.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.saverio.golditemexpansion.effect.GodNegativeStatusEffect;
import org.saverio.golditemexpansion.effect.GodPositiveStatusEffect;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.WeakHashMap;

@Mixin(LivingEntity.class)
public final class LivingEntityMixin {
    @Unique
    private static final WeakHashMap<LivingEntity, Boolean> golditemexpansion$SKIP = new WeakHashMap<>();
    @Unique
    private static void golditemexpansion$setSkip(LivingEntity entity, boolean skip) {
        if (skip) {
            golditemexpansion$SKIP.put(entity, Boolean.TRUE);
        } else {
            golditemexpansion$SKIP.remove(entity);
        }
    }
    @Unique
    private static boolean golditemexpansion$shouldSkip(LivingEntity entity) {
        return golditemexpansion$SKIP.containsKey(entity);
    }
    @Inject(method = "removeAllEffects", at = @At("HEAD"))
    private void onRemoveAllEffectsStart(CallbackInfoReturnable<Boolean> cir) {
        golditemexpansion$setSkip((LivingEntity)(Object)this, true);
    }
    @Inject(method = "removeAllEffects", at = @At("RETURN"))
    private void onRemoveAllEffectsEnd(CallbackInfoReturnable<Boolean> cir) {
        golditemexpansion$setSkip((LivingEntity)(Object)this, false);
    }
    @Inject(method = "onEffectsRemoved", at = @At("HEAD"))
    private void onEffectsRemoved(Collection<MobEffectInstance> effects, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (golditemexpansion$shouldSkip(entity)) return;
        for (MobEffectInstance instance : effects) {
            Holder<MobEffect> removed = instance.getEffect();
            if (removed.equals(ModEffects.godPositiveHolder())) {
                for (Holder<MobEffect> sub : GodPositiveStatusEffect.GOD_POSITIVE_EFFECTS.keySet()) {
                    entity.removeEffect(sub);
                }
            } else if (removed.equals(ModEffects.godNegativeHolder())) {
                for (Holder<MobEffect> sub : GodNegativeStatusEffect.GOD_NEGATIVE_EFFECTS.keySet()) {
                    entity.removeEffect(sub);
                }
            }
        }
    }
}