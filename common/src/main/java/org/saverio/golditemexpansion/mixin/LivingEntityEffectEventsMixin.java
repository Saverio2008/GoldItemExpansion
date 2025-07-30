package org.saverio.golditemexpansion.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.saverio.golditemexpansion.event.EffectChangeListenerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityEffectEventsMixin {

    @Inject(method = "onEffectAdded", at = @At("TAIL"))
    private void onEffectAddedInject(MobEffectInstance effectInstance, @Nullable Entity entity, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;
        EffectChangeListenerManager.onEffectAdded(self, effectInstance, entity);
    }

    @Inject(method = "onEffectRemoved", at = @At("TAIL"))
    private void onEffectRemovedInject(MobEffectInstance effectInstance, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;
        EffectChangeListenerManager.onEffectRemoved(self, effectInstance);
    }
}
