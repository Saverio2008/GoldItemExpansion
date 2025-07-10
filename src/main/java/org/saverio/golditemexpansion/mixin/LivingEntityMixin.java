package org.saverio.golditemexpansion.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.saverio.golditemexpansion.util.LivingEntityAccessor;

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
}