package org.saverio.golditemexpansion.fabric.registry.potion;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import org.saverio.golditemexpansion.potion.ModBrewingRecipes;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;
import static org.saverio.golditemexpansion.fabric.registry.item.ModItems.GOLDEN_HEAD_BLOCK_ITEM;
import static org.saverio.golditemexpansion.potion.ModPotionInstances.*;

public final class ModPotions {
    public static void registerPotions() {
        registerPotion("god", GOD_POTION);
        registerPotion("undead_god", UNDEAD_GOD_POTION);
        registerPotion("arthropod_god", ARTHROPOD_GOD_POTION);
        registerPotion("healing_v", HEALING_V);
        registerPotion("godly_healing", GODLY_HEALING);

        registerItem("god_potion", GOD_POTION_ITEM);
        registerItem("undead_god_potion", UNDEAD_GOD_POTION_ITEM);
        registerItem("arthropod_god_potion", ARTHROPOD_GOD_POTION_ITEM);
        registerItem("healing_v", HEALING_V_ITEM);
        registerItem("godly_healing", GODLY_HEALING_ITEM);

        ModBrewingRecipes.registerCommonBrewingRecipes(GOLDEN_HEAD_BLOCK_ITEM);
    }

    private static void registerPotion(String name, Potion potion) {
        Registry.register(BuiltInRegistries.POTION, new ResourceLocation(MOD_ID, name), potion);
    }

    private static void registerItem(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, name), item);
    }
}