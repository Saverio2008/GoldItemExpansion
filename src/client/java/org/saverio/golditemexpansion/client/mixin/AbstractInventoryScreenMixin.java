package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.client.network.ClientPlayerEntity;
import org.saverio.golditemexpansion.util.EffectUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;
import java.util.stream.Collectors;

@Mixin(AbstractInventoryScreen.class)
public class AbstractInventoryScreenMixin {

    @Redirect(
            method = "drawStatusEffects(Lnet/minecraft/client/gui/DrawContext;II)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;getStatusEffects()Ljava/util/Collection;"
            )
    )
    private Collection<StatusEffectInstance> filterHiddenStatusEffects(ClientPlayerEntity player) {
        return player.getStatusEffects().stream()
                .filter(effect -> !EffectUtils.shouldHideIcon(effect))
                .collect(Collectors.toList());
    }
}