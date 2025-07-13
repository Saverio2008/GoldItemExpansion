package org.saverio.golditemexpansion.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import org.saverio.golditemexpansion.potion.ModPotions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "canCombine", at = @At("HEAD"), cancellable = true)
    private static void canCombine(ItemStack stack, ItemStack otherStack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(Items.POTION) && otherStack.isOf(Items.POTION)) {
            if ((PotionUtil.getPotion(stack) == ModPotions.HEALING_V && PotionUtil.getPotion(otherStack) == Potions.STRONG_HEALING) ||
                    (PotionUtil.getPotion(otherStack) == ModPotions.HEALING_V && PotionUtil.getPotion(stack) == Potions.STRONG_HEALING)) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }
}
