package org.saverio.golditemexpansion.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "addStatusEffect*", at = @At("HEAD"))
    private void beforeAddGodEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (effect.getEffectType() == ModEffects.GOD_STATUS_EFFECT) {
            LivingEntity self = (LivingEntity) (Object) this;
            self.removeStatusEffect(ModEffects.GOD_STATUS_EFFECT);
        }
    }
}