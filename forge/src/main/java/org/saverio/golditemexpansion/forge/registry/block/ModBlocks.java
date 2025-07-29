package org.saverio.golditemexpansion.forge.registry.block;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.block.CompressedGoldBlock;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Golditemexpansion.MOD_ID);

    public static final RegistryObject<Block> COMPRESSED_GOLD_BLOCK =
            BLOCKS.register("compressed_gold_block", () -> CompressedGoldBlock.COMPRESSED_GOLD_BLOCK);

    public static void registerBlocks(IEventBus bus) {
        BLOCKS.register(bus);
    }
}
