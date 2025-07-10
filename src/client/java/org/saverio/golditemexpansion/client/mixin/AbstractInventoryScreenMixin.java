package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.saverio.golditemexpansion.effect.ModEffects;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin {

    @Inject(method = "drawStatusEffects", at = @At("HEAD"), cancellable = true)
    private void onDrawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && player.hasStatusEffect(ModEffects.GOD_STATUS_EFFECT)) {
            ci.cancel();
        }
    }
}
