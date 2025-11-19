package org.saverio.golditemexpansion.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import org.saverio.golditemexpansion.util.GodEffectSkipManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MilkBucketItem.class)
public abstract class MilkBucketItemMixin {
    @Inject(method = "finishUsingItem", at = @At("HEAD"))
    private void onMilkDrinkStart(ItemStack stack, Level level,
                                  LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        GodEffectSkipManager.setSkip(entity, true);
    }
    @Inject(method = "finishUsingItem", at = @At("RETURN"))
    private void onMilkDrinkEnd(ItemStack stack, Level level,
                                LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        GodEffectSkipManager.setSkip(entity, false);
    }
}
