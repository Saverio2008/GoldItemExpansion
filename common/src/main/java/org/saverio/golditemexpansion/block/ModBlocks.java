package org.saverio.golditemexpansion.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

public final class ModBlocks {
    public static final Block COMPRESSED_GOLD_BLOCK = new Block(
            BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)
                    .strength(8.0f, 1200.0f)
    );

    public static void registerBlocks() {
        Registry.register(
                BuiltInRegistries.BLOCK,
                new ResourceLocation(MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK
        );
    }
}
