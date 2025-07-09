package org.saverio.golditemexpansion.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.block.ModBlocks;

public class ModItems {
    public static final Item COMPRESSED_GOLD_BLOCK_ITEM =
            new BlockItem(ModBlocks.COMPRESSED_GOLD_BLOCK, new FabricItemSettings());

    public static final Item GOLDEN_HEAD_ITEM =
            new BlockItem(ModBlocks.GOLDEN_HEAD_BLOCK, new FabricItemSettings());

    @SuppressWarnings("UnstableApiUsage")
    public static void registerItems() {
        Registry.register(Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK_ITEM);

        Registry.register(Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "golden_head"),
                GOLDEN_HEAD_ITEM);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content ->
                content.addAfter(Blocks.GOLD_BLOCK.asItem(), COMPRESSED_GOLD_BLOCK_ITEM));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content ->
                content.addAfter(Blocks.DRAGON_HEAD.asItem(), GOLDEN_HEAD_ITEM));
    }
}