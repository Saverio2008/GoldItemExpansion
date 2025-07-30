package org.saverio.golditemexpansion.block;

import net.minecraft.world.level.block.state.BlockBehaviour;

public final class GoldenHeadBlock {
    public static final GoldenHead GOLDEN_HEAD_BLOCK = new GoldenHead(
            BlockBehaviour.Properties.of()
                    .strength(1.0f)
                    .noOcclusion()
    );
}