package org.saverio.golditemexpansion.forge.registry.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.block.ModBlockInstances;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Golditemexpansion.MOD_ID);
    @SuppressWarnings("unused")
    public static final RegistryObject<Block> COMPRESSED_GOLD_BLOCK =
            BLOCKS.register("compressed_gold_block", () -> ModBlockInstances.COMPRESSED_GOLD_BLOCK);
    @SuppressWarnings("unused")
    public static final RegistryObject<Block> GOLDEN_HEAD_BLOCK =
            BLOCKS.register("golden_head", () -> ModBlockInstances.GOLDEN_HEAD_BLOCK);
}
