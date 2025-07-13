package org.saverio.golditemexpansion.potion;

import net.minecraft.item.Items;
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
        BrewingRecipeRegistry.registerPotionRecipe(
                ModPotions.GOD_POTION,
                Items.ROTTEN_FLESH,
                ModPotions.UNDEAD_GOD_POTION
        );
        BrewingRecipeRegistry.registerPotionRecipe(
                ModPotions.GOD_POTION,
                Items.SPIDER_EYE,
                ModPotions.ARTHROPOD_GOD_POTION
        );
    }
}