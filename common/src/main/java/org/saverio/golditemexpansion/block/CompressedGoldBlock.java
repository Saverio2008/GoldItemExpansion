package org.saverio.golditemexpansion.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class CompressedGoldBlock {
    public static final Block COMPRESSED_GOLD_BLOCK = new Block(
            BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)
                    .strength(8.0f, 1200.0f)
    );
}
