package org.saverio.golditemexpansion.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.saverio.golditemexpansion.util.LivingEntityUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.saverio.golditemexpansion.util.LivingEntityAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityAccessor {
    @Unique
    private static final TrackedData<Boolean> GOD_NEGATIVE_APPLIED = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Unique
    private static final TrackedData<Boolean> GOD_POSITIVE_APPLIED = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    @Unique
    private static final TrackedData<Boolean> GOD_STATUS_APPLIED = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Unique
    private LivingEntity self() {
        return (LivingEntity)(Object)this;
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initDataTracker(CallbackInfo ci) {
        self().getDataTracker().startTracking(GOD_NEGATIVE_APPLIED, false);
        self().getDataTracker().startTracking(GOD_POSITIVE_APPLIED, false);
        self().getDataTracker().startTracking(GOD_STATUS_APPLIED, false);
    }

    // 负面效果标记访问器
    @Override
    public boolean goldItemExpansion$isGodNegativeApplied() {
        return self().getDataTracker().get(GOD_NEGATIVE_APPLIED);
    }

    @Override
    public void goldItemExpansion$setGodNegativeApplied(boolean applied) {
        self().getDataTracker().set(GOD_NEGATIVE_APPLIED, applied);
    }

    // 正面效果标记访问器
    @Override
    public boolean goldItemExpansion$isGodPositiveApplied() {
        return self().getDataTracker().get(GOD_POSITIVE_APPLIED);
    }

    @Override
    public void goldItemExpansion$setGodPositiveApplied(boolean applied) {
        self().getDataTracker().set(GOD_POSITIVE_APPLIED, applied);
    }

    // 主God状态效果标记访问器
    @Override
    public boolean goldItemExpansion$isGodStatusApplied() {
        return self().getDataTracker().get(GOD_STATUS_APPLIED);
    }

    @Override
    public void goldItemExpansion$setGodStatusApplied(boolean applied) {
        self().getDataTracker().set(GOD_STATUS_APPLIED, applied);
    }
    @Inject(method = "removeStatusEffect", at = @At("HEAD"))
    private void onRemoveStatusEffect(StatusEffect effect, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        if (effect == ModEffects.GOD_NEGATIVE_EFFECT) {
            LivingEntityUtils.setGodNegativeApplied(self, false);
        } else if (effect == ModEffects.GOD_POSITIVE_EFFECT) {
            LivingEntityUtils.setGodPositiveApplied(self, false);
        } else if (effect == ModEffects.GOD_STATUS_EFFECT) {
            LivingEntityUtils.setGodStatusApplied(self, false);
        }
    }

    @Inject(method = "clearStatusEffects", at = @At("HEAD"))
    private void onClearStatusEffects(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity)(Object)this;
        LivingEntityUtils.setGodNegativeApplied(self, false);
        LivingEntityUtils.setGodPositiveApplied(self, false);
        LivingEntityUtils.setGodStatusApplied(self, false);
    }
}