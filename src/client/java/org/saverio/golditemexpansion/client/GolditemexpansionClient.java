package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.saverio.golditemexpansion.client.mixin.BlockEntityTypeAccessor;
import org.saverio.golditemexpansion.block.ModBlocks;

import java.util.HashSet;
import java.util.Set;

public class GolditemexpansionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityTypeAccessor skullAccessor = (BlockEntityTypeAccessor) BlockEntityType.SKULL;
        Set<Block> original = skullAccessor.getBlocks();
        Set<Block> modifiable = new HashSet<>(original);
        modifiable.add(ModBlocks.GOLDEN_HEAD_BLOCK);
        skullAccessor.setBlocks(modifiable);
    }
}
