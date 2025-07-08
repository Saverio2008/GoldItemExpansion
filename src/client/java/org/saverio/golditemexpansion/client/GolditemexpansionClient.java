package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import org.saverio.golditemexpansion.client.mixin.BlockEntityTypeAccessor;
import org.saverio.golditemexpansion.block.ModBlocks;

public class GolditemexpansionClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityTypeAccessor skullAccessor = (BlockEntityTypeAccessor) BlockEntityType.SKULL;
        skullAccessor.getBlocks().add(ModBlocks.GOLDEN_HEAD_BLOCK);
    }
}
