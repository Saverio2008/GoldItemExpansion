package org.saverio.golditemexpansion.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModBlockInstances {
    public static final Block COMPRESSED_GOLD_BLOCK = new Block(
            BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)
                    .strength(8.0f, 1200.0f)
    );

    public static final GoldenHeadBlock GOLDEN_HEAD_BLOCK = new GoldenHeadBlock(
            BlockBehaviour.Properties.of()
                    .strength(1.0f)
                    .noOcclusion()
    );
}
