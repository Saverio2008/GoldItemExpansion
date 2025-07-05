package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import org.saverio.golditemexpansion.block.ModBlocks;
import org.saverio.golditemexpansion.item.ModItems;

public class GolditemexpansionClient implements ClientModInitializer {
    public static final int GOLD_TINT = 0xFFFFD700;
    @Override
    public void onInitializeClient() {
        BlockColorProvider goldenHeadBlockColorProvider =
                (state, world, pos, tintIndex) -> GOLD_TINT;
        ItemColorProvider goldenHeadItemColorProvider = (stack, tintIndex) -> GOLD_TINT;
        ColorProviderRegistry.BLOCK.register(goldenHeadBlockColorProvider, ModBlocks.GOLDEN_HEAD_BLOCK);
        ColorProviderRegistry.ITEM.register(goldenHeadItemColorProvider, ModItems.GOLDEN_HEAD_ITEM);
    }
}
