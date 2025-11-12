package org.saverio.golditemexpansion.potion;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import org.saverio.golditemexpansion.util.BrewingRecipeUtil;

import static org.saverio.golditemexpansion.potion.ModPotionInstances.*;

public final class ModBrewingRecipes {
    public static void registerCommonBrewingRecipes(Item goldenHeadItem) {
        addPotionRecipe(Potions.AWKWARD, goldenHeadItem, GOD_POTION);
        addPotionRecipe(GOD_POTION, Items.ROTTEN_FLESH, UNDEAD_GOD_POTION);
        addPotionRecipe(GOD_POTION, Items.SPIDER_EYE, ARTHROPOD_GOD_POTION);
        addPotionRecipe(Potions.AWKWARD, Items.GOLDEN_APPLE, HEALING_V);
        addPotionRecipe(Potions.AWKWARD, Items.ENCHANTED_GOLDEN_APPLE, GODLY_HEALING);
    }

    private static void addPotionRecipe(Potion input, Item ingredient, Potion output) {
        BrewingRecipeUtil.addMix(input, ingredient, output);
    }
}
