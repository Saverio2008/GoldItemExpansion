package org.saverio.golditemexpansion.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.saverio.golditemexpansion.effect.GodNegativeStatusEffect;
import org.saverio.golditemexpansion.effect.GodPositiveStatusEffect;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.saverio.golditemexpansion.util.GodEffectSkipManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public final class LivingEntityMixin {
    @Inject(method = "removeAllEffects", at = @At("HEAD"))
    private void onRemoveAllEffectsStart(CallbackInfoReturnable<Boolean> cir) {
        GodEffectSkipManager.setSkip((LivingEntity)(Object)this, true);
    }
    @Inject(method = "removeAllEffects", at = @At("RETURN"))
    private void onRemoveAllEffectsEnd(CallbackInfoReturnable<Boolean> cir) {
        GodEffectSkipManager.setSkip((LivingEntity)(Object)this, false);
    }
    @Inject(method = "onEffectRemoved", at = @At("HEAD"))
    private void onEffectRemoved(MobEffectInstance mobEffectInstance, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (GodEffectSkipManager.shouldSkip(entity)) return;
        Holder<MobEffect> removed = mobEffectInstance.getEffect();
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