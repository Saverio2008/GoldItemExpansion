package org.saverio.golditemexpansion.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.saverio.golditemexpansion.util.HiddenEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public class StatusEffectInstanceMixin implements HiddenEffectInstance {
    @Unique
    private boolean goldItemExpansion$hideIcon = false;

    @Override
    public boolean goldItemExpansion$shouldHideIcon() {
        return this.goldItemExpansion$hideIcon;
    }

    @Override
    public void goldItemExpansion$setHideIcon(boolean hide) {
        this.goldItemExpansion$hideIcon = hide;
    }

    @Inject(method = "shouldShowIcon()Z", at = @At("HEAD"), cancellable = true)
    private void onShouldShowIcon(CallbackInfoReturnable<Boolean> cir) {
        if (goldItemExpansion$hideIcon) {
            cir.setReturnValue(false);
        }
    }
}