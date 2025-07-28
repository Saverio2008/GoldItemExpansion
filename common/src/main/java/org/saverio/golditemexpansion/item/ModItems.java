package org.saverio.golditemexpansion.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.saverio.golditemexpansion.block.ModBlocks;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

public final class ModItems {
    public static final Item COMPRESSED_GOLD_BLOCK_ITEM = new BlockItem(ModBlocks.COMPRESSED_GOLD_BLOCK, new Item.Properties());

    public static void registerItems() {
        Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK_ITEM
        );
    }
}