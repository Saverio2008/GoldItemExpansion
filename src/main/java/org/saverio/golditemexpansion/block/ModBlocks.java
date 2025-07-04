package org.saverio.golditemexpansion.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;

public class ModBlocks {
    public static final Block COMPRESSED_GOLD_BLOCK = new Block(
            AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)
                    .strength(8.0f, 1200.0f)
    );

    public static final Block GOLDEN_HEAD_BLOCK = new GoldenHeadBlock(
            AbstractBlock.Settings.copy(Blocks.SKELETON_SKULL)
                    .strength(1.0f)
                    .nonOpaque()
                    .ticksRandomly()
    );

    public static void registerBlocks() {
        Registry.register(Registries.BLOCK,
                new Identifier(Golditemexpansion.MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK);

        Registry.register(Registries.BLOCK,
                new Identifier(Golditemexpansion.MOD_ID, "golden_head"),
                GOLDEN_HEAD_BLOCK);
    }
}