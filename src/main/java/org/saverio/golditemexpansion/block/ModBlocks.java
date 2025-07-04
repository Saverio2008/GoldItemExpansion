package org.saverio.golditemexpansion.block;

import net.minecraft.block.Block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;

public class ModBlocks {
    public static final Block COMPRESSED_GOLD_BLOCK = new Block(
            AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)
                    .strength(8.0f, 1200.0f)
    );
    public static void registerBlocks() {
        Registry.register(Registries.BLOCK,
                new Identifier(Golditemexpansion.MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK);
    }
}
