package org.saverio.golditemexpansion.fabric.registry.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;
import static org.saverio.golditemexpansion.block.CompressedGoldBlock.COMPRESSED_GOLD_BLOCK;
import static org.saverio.golditemexpansion.block.GoldenHeadBlock.GOLDEN_HEAD_BLOCK;

public final class ModItems {
    public static final Item COMPRESSED_GOLD_BLOCK_ITEM =
            new BlockItem(COMPRESSED_GOLD_BLOCK, new Item.Properties());

    public static final Item GOLDEN_HEAD_BLOCK_ITEM =
            new BlockItem(GOLDEN_HEAD_BLOCK, new Item.Properties());

    @SuppressWarnings("UnstableApiUsage")
    public static void registerItems() {
        Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK_ITEM
        );
        Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(MOD_ID, "golden_head"),
                GOLDEN_HEAD_BLOCK_ITEM
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries ->
                entries.addAfter(Items.GOLD_BLOCK, COMPRESSED_GOLD_BLOCK_ITEM));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries ->
                entries.addAfter(Items.DRAGON_HEAD, GOLDEN_HEAD_BLOCK_ITEM));
    }
}