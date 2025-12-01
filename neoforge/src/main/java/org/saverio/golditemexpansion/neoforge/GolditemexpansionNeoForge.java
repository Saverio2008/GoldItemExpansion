package org.saverio.golditemexpansion.neoforge;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.block.ModBlocks;
import org.saverio.golditemexpansion.item.ModItems;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.saverio.golditemexpansion.neoforge.events.CreativeTabEvents;
import org.saverio.golditemexpansion.potion.ModPotions;

@Mod(Golditemexpansion.MOD_ID)
public final class GolditemexpansionNeoForge {
    public GolditemexpansionNeoForge() {
        ModBlocks.BLOCKS.register();
        ModItems.ITEMS.register();
        ModEffects.EFFECTS.register();
        ModPotions.POTIONS.register();
        Golditemexpansion.init();
        registerBrewingRecipes();
        CreativeTabEvents.register();
    }
    private void registerBrewingRecipes() {
        NeoForge.EVENT_BUS.addListener(RegisterBrewingRecipesEvent.class, event -> {
            event.getBuilder().addMix(
                    Potions.AWKWARD,
                    ModItems.GOLDEN_HEAD_BLOCK_ITEM.get(),
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.GOD_POTION.getKey())
            );
            event.getBuilder().addMix(
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.GOD_POTION.getKey()),
                    Items.ROTTEN_FLESH,
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.UNDEAD_GOD_POTION.getKey())
            );
            event.getBuilder().addMix(
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.GOD_POTION.getKey()),
                    Items.SPIDER_EYE,
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.ARTHROPOD_GOD_POTION.getKey())
            );
            event.getBuilder().addMix(
                    Potions.AWKWARD,
                    Items.GOLDEN_APPLE,
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.HEALING_III.getKey())
            );
            event.getBuilder().addMix(
                    Potions.AWKWARD,
                    Items.ENCHANTED_GOLDEN_APPLE,
                    BuiltInRegistries.POTION.getOrThrow(ModPotions.GODLY_HEALING.getKey())
            );
        });
    }
}
