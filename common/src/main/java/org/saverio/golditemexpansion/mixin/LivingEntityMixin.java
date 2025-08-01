package org.saverio.golditemexpansion.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.saverio.golditemexpansion.effect.ModEffectInstances;
import org.saverio.golditemexpansion.event.EffectChangeListenerManager;
import org.saverio.golditemexpansion.util.GodEffectRemoveSkipManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(
            method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD")
    )
    private void beforeAddGodEffectWithSource(MobEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        if (effect.getEffect() == ModEffectInstances.GOD_STATUS_EFFECT) {
            LivingEntity self = (LivingEntity)(Object)this;
            MobEffectInstance current = self.getEffect(ModEffectInstances.GOD_STATUS_EFFECT);
            if (current != null && current.getAmplifier() != effect.getAmplifier()) {
                self.removeEffect(ModEffectInstances.GOD_STATUS_EFFECT);
            }
        }
    }

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

    @Inject(method = "removeAllEffects", at = @At("HEAD"))
    private void onRemoveAllEffectsStart(CallbackInfoReturnable<Boolean> cir) {
        GodEffectRemoveSkipManager.setSkip(true);
    }

    @Inject(method = "removeAllEffects", at = @At("RETURN"))
    private void onRemoveAllEffectsEnd(CallbackInfoReturnable<Boolean> cir) {
        GodEffectRemoveSkipManager.clear();
    }
}
