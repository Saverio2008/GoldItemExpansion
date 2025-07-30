package org.saverio.golditemexpansion.fabric.registry.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;
import static org.saverio.golditemexpansion.block.CompressedGoldBlock.COMPRESSED_GOLD_BLOCK;
import static org.saverio.golditemexpansion.block.GoldenHeadBlock.GOLDEN_HEAD_BLOCK;

public final class ModBlocks {

    public static void registerBlocks() {
        Registry.register(
                BuiltInRegistries.BLOCK,
                new ResourceLocation(MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK
        );
        Registry.register(
                BuiltInRegistries.BLOCK,
                new ResourceLocation(MOD_ID, "golden_head"),
                GOLDEN_HEAD_BLOCK
        );
    }
}
