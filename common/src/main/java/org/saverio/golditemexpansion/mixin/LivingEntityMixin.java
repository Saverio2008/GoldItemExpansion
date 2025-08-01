package org.saverio.golditemexpansion.mixin;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.saverio.golditemexpansion.effect.ModEffectInstances;
import org.saverio.golditemexpansion.event.EffectChangeListenerManager;
import org.saverio.golditemexpansion.util.GodEffectRemoveSkipManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    private static final Map<LivingEntity, Long> golditemexpansion$lastRemoveEffectTick = new ConcurrentHashMap<>();
    @Unique
    private static final Map<LivingEntity, Boolean> golditemexpansion$removeEffectLock = new ConcurrentHashMap<>();
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
        EffectChangeListenerManager.onEffectAdded((LivingEntity)(Object)this, effectInstance, entity);
    }

    @Inject(method = "onEffectRemoved", at = @At("TAIL"))
    private void onEffectRemovedInject(MobEffectInstance effectInstance, CallbackInfo ci) {
        EffectChangeListenerManager.onEffectRemoved((LivingEntity)(Object)this, effectInstance);
    }

    @Inject(method = "removeAllEffects", at = @At("HEAD"))
    private void onRemoveAllEffectsStart(CallbackInfoReturnable<Boolean> cir) {
        GodEffectRemoveSkipManager.setSkip((LivingEntity)(Object)this, true);
    }

    @Inject(method = "removeAllEffects", at = @At("RETURN"))
    private void onRemoveAllEffectsEnd(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        GodEffectRemoveSkipManager.setSkip(self, false);
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void onEntityRemoved(CallbackInfo ci) {
        LivingEntity self = (LivingEntity)(Object)this;
        GodEffectRemoveSkipManager.setSkip(self, false);
        GodEffectRemoveSkipManager.setForgeSkip(self, false);
    }

    @Inject(method = "removeEffectNoUpdate", at = @At("HEAD"))
    private void onRemoveEffectNoUpdateHead(@Nullable MobEffect mobEffect, CallbackInfoReturnable<MobEffectInstance> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        long currentTick = self.level().getGameTime();

        Long lastTick = golditemexpansion$lastRemoveEffectTick.get(self);
        if (lastTick != null && (currentTick - lastTick) <= 10) {
            golditemexpansion$removeEffectLock.put(self, true);
        }
        golditemexpansion$lastRemoveEffectTick.put(self, currentTick);

        if (Boolean.TRUE.equals(golditemexpansion$removeEffectLock.get(self))) {
            GodEffectRemoveSkipManager.setForgeSkip(self, true);
        }
    }

    @Inject(method = "removeEffectNoUpdate", at = @At("RETURN"))
    private void onRemoveEffectNoUpdateReturn(@Nullable MobEffect mobEffect, CallbackInfoReturnable<MobEffectInstance> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        if (Boolean.TRUE.equals(golditemexpansion$removeEffectLock.get(self))) {
            GodEffectRemoveSkipManager.setForgeSkip(self, false);
            golditemexpansion$removeEffectLock.remove(self);
        }
    }
}
