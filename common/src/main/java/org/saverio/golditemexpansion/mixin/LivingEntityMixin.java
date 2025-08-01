package org.saverio.golditemexpansion.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.saverio.golditemexpansion.effect.ModEffectInstances;
import org.saverio.golditemexpansion.event.EffectChangeListenerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    private boolean golditemexpansion$addingGodEffect = false;

    @Inject(
            method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At("HEAD"),
            cancellable = true)
    private void beforeAddGodEffectWithSource(MobEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        if (effect.getEffect() == ModEffectInstances.GOD_STATUS_EFFECT) {
            LivingEntity self = (LivingEntity)(Object)this;
            if (golditemexpansion$addingGodEffect) return;
            golditemexpansion$addingGodEffect = true;
            self.removeEffect(ModEffectInstances.GOD_STATUS_EFFECT);
            boolean applied = self.addEffect(new MobEffectInstance(
                    effect.getEffect(),
                    effect.getDuration(),
                    effect.getAmplifier(),
                    effect.isAmbient(),
                    effect.isVisible(),
                    effect.showIcon()
            ), source);
            golditemexpansion$addingGodEffect = false;
            cir.setReturnValue(applied);
        }
    }

    @Inject(method = "onEffectAdded", at = @At("TAIL"))
    private void onEffectAddedInject(MobEffectInstance effectInstance, @Nullable Entity entity, CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;
        EffectChangeListenerManager.onEffectAdded(self, effectInstance, entity);
    }
}
