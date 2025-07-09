package org.saverio.golditemexpansion.potion;

import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import org.saverio.golditemexpansion.item.ModItems;

public class ModBrewingRecipes {
    public static void registerBrewingRecipes() {
        BrewingRecipeRegistry.registerPotionRecipe(
                Potions.AWKWARD,
                ModItems.GOLDEN_HEAD_ITEM,
                ModPotions.GOD_POTION
        );
    }
}