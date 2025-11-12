package org.saverio.golditemexpansion.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PotionBrewing.class)
public interface PotionBrewingAccessor {
    @Invoker("addMix")
    static void golditemexpansion$addMix(Potion from, Item ingredient, Potion to) {
        throw new UnsupportedOperationException("Mixin failed to apply!");
    }
}
