package org.saverio.golditemexpansion.block;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.saverio.golditemexpansion.Golditemexpansion;

public final class ModBlocks {
    private ModBlocks() {}
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Golditemexpansion.MOD_ID, Registries.BLOCK);
    public static final RegistrySupplier<Block> COMPRESSED_GOLD_BLOCK =
            BLOCKS.register("compressed_gold_block",
                    () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)
                            .strength(8.0f, 1200.0f)));
    public static final RegistrySupplier<GoldenHeadBlock> GOLDEN_HEAD_BLOCK =
            BLOCKS.register("golden_head",
                    () -> new GoldenHeadBlock(BlockBehaviour.Properties.of()
                            .strength(1.0f)
                            .noOcclusion()));
}