package org.saverio.golditemexpansion.mixin;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

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
}