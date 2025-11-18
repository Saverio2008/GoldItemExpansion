package org.saverio.golditemexpansion.fabric.registry.potion;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import org.saverio.golditemexpansion.fabric.registry.item.ModItems;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;
import static org.saverio.golditemexpansion.potion.ModPotionInstances.*;

public final class ModPotions {
    public static void registerPotions() {
        registerPotion("god_potion", GOD_POTION);
        registerPotion("undead_god_potion", UNDEAD_GOD_POTION);
        registerPotion("arthropod_god_potion", ARTHROPOD_GOD_POTION);
        registerPotion("healing_iii", HEALING_III);
        registerPotion("godly_healing", GODLY_HEALING);
        registerBrewingRecipes();
    }

    private static void registerPotion(String name, Potion potion) {
        Registry.register(BuiltInRegistries.POTION, new ResourceLocation(MOD_ID, name), potion);
    }

    private static void registerBrewingRecipes() {
        FabricBrewingRecipeRegistry.registerPotionRecipe(
                Potions.AWKWARD, Ingredient.of(ModItems.GOLDEN_HEAD_BLOCK_ITEM), GOD_POTION);
        FabricBrewingRecipeRegistry.registerPotionRecipe(
                GOD_POTION, Ingredient.of(Items.ROTTEN_FLESH), UNDEAD_GOD_POTION);
        FabricBrewingRecipeRegistry.registerPotionRecipe(
                GOD_POTION, Ingredient.of(Items.SPIDER_EYE), ARTHROPOD_GOD_POTION);
        FabricBrewingRecipeRegistry.registerPotionRecipe(
                Potions.AWKWARD, Ingredient.of(Items.GOLDEN_APPLE), HEALING_III);
        FabricBrewingRecipeRegistry.registerPotionRecipe(
                Potions.AWKWARD, Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE), GODLY_HEALING);
    }
}