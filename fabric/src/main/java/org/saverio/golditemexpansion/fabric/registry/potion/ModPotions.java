package org.saverio.golditemexpansion.fabric.registry.potion;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.Potion;
import org.saverio.golditemexpansion.potion.ModBrewingRecipes;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;
import static org.saverio.golditemexpansion.fabric.registry.item.ModItems.GOLDEN_HEAD_BLOCK_ITEM;
import static org.saverio.golditemexpansion.potion.ModPotionInstances.*;

public final class ModPotions {
    public static void registerPotions() {
        registerPotion("god_potion", GOD_POTION);
        registerPotion("undead_god_potion", UNDEAD_GOD_POTION);
        registerPotion("arthropod_god_potion", ARTHROPOD_GOD_POTION);
        registerPotion("healing_v", HEALING_V);
        registerPotion("godly_healing", GODLY_HEALING);

        ModBrewingRecipes.registerCommonBrewingRecipes(GOLDEN_HEAD_BLOCK_ITEM);
    }

    private static void registerPotion(String name, Potion potion) {
        Registry.register(BuiltInRegistries.POTION, new ResourceLocation(MOD_ID, name), potion);
    }
}