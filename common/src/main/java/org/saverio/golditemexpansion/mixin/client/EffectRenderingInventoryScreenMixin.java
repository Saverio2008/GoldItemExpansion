package org.saverio.golditemexpansion.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.saverio.golditemexpansion.effect.ModEffectInstances;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.saverio.golditemexpansion.effect.GodNegativeStatusEffect.GOD_NEGATIVE_EFFECTS;
import static org.saverio.golditemexpansion.effect.GodPositiveStatusEffect.GOD_POSITIVE_EFFECTS;

@Mixin(EffectRenderingInventoryScreen.class)
public final class EffectRenderingInventoryScreenMixin {
    @ModifyVariable(
            method = "renderEffects",
            at = @At(value = "STORE", ordinal = 0)
    )
    private Collection<MobEffectInstance> modifyActiveEffects(Collection<MobEffectInstance> originalCollection) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return originalCollection;
        }
        boolean hidePositive = player.hasEffect(ModEffectInstances.GOD_POSITIVE_EFFECT);
        boolean hideNegative = player.hasEffect(ModEffectInstances.GOD_NEGATIVE_EFFECT);

        return originalCollection.stream().filter(effectInstance -> {
            MobEffect effect = effectInstance.getEffect();
            if (effect.equals(ModEffectInstances.GOD_STATUS_EFFECT)) {
                return false;
            } else if (hidePositive && GOD_POSITIVE_EFFECTS.containsKey(effect)) {
                return false;
            } else if (hideNegative && GOD_NEGATIVE_EFFECTS.containsKey(effect)) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());
    }
}
