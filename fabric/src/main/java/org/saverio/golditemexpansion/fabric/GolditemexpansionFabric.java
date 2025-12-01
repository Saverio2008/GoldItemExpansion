package org.saverio.golditemexpansion.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.block.ModBlocks;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.saverio.golditemexpansion.item.ModItems;
import org.saverio.golditemexpansion.potion.ModPotions;

public final class GolditemexpansionFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModBlocks.BLOCKS.register();
        ModItems.ITEMS.register();
        ModEffects.EFFECTS.register();
        ModPotions.POTIONS.register();
        Golditemexpansion.init();
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries ->
                entries.addAfter(Items.GOLD_BLOCK,
                        ModItems.COMPRESSED_GOLD_BLOCK_ITEM.get()));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries ->
                entries.addAfter(Items.DRAGON_HEAD,
                        ModItems.GOLDEN_HEAD_BLOCK_ITEM.get()));
        registerBrewingRecipes();
    }
    private void registerBrewingRecipes() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                    Potions.AWKWARD,
                    Ingredient.of(ModItems.GOLDEN_HEAD_BLOCK_ITEM.get()),
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.GOD_POTION.getKey())
            );
            builder.registerPotionRecipe(
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.GOD_POTION.getKey()),
                    Ingredient.of(Items.ROTTEN_FLESH),
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.UNDEAD_GOD_POTION.getKey())
            );
            builder.registerPotionRecipe(
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.GOD_POTION.getKey()),
                    Ingredient.of(Items.SPIDER_EYE),
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.ARTHROPOD_GOD_POTION.getKey())
            );
            builder.registerPotionRecipe(
                    Potions.AWKWARD,
                    Ingredient.of(Items.GOLDEN_APPLE),
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.HEALING_III.getKey())
            );
            builder.registerPotionRecipe(
                    Potions.AWKWARD,
                    Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE),
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.GODLY_HEALING.getKey())
            );
        });
    }
}
