package org.saverio.golditemexpansion.effect;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import static org.saverio.golditemexpansion.block.CompressedGoldBlock.COMPRESSED_GOLD_BLOCK;

public final class CompressedGoldBlockItem {
    public static final Item COMPRESSED_GOLD_BLOCK_ITEM =
            new BlockItem(COMPRESSED_GOLD_BLOCK, new Item.Properties());
}
