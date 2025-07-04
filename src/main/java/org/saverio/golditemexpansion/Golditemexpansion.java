package org.saverio.golditemexpansion;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import org.saverio.golditemexpansion.item.ModItems;
import org.saverio.golditemexpansion.block.ModBlocks;
import net.fabricmc.api.ModInitializer;

public class Golditemexpansion implements ModInitializer {
    public static final String MOD_ID = "golditemexpansion";
    @Override
    public void onInitialize() {
        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content ->
                content.addAfter(net.minecraft.block.Blocks.GOLD_BLOCK.asItem(), ModItems.COMPRESSED_GOLD_BLOCK_ITEM));
    }
}