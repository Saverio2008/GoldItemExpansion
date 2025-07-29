package org.saverio.golditemexpansion.fabric.registry.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;
import static org.saverio.golditemexpansion.item.CompressedGoldBlockItem.COMPRESSED_GOLD_BLOCK_ITEM;

public final class ModItems {
    @SuppressWarnings("UnstableApiUsage")
    public static void registerItems() {
        Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK_ITEM
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries ->
                entries.addAfter(Items.GOLD_BLOCK, COMPRESSED_GOLD_BLOCK_ITEM));
    }
}