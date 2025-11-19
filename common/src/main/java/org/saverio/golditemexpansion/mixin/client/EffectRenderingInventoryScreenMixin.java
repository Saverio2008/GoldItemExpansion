package org.saverio.golditemexpansion.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.saverio.golditemexpansion.effect.ModEffects;
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
        boolean hidePositive = player.hasEffect(ModEffects.godPositiveHolder());
        boolean hideNegative = player.hasEffect(ModEffects.godNegativeHolder());
        return originalCollection.stream().filter(effectInstance -> {
            Holder<MobEffect> holder = effectInstance.getEffect();
            if (ModEffects.isGodMainEffect(holder)) {
                return false;
            } else if (hidePositive && GOD_POSITIVE_EFFECTS.containsKey(holder)) {
                return false;
            } else return !hideNegative || !GOD_NEGATIVE_EFFECTS.containsKey(holder);
        }).collect(Collectors.toList());
    }
}
