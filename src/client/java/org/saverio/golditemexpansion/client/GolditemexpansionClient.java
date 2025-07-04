package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import org.saverio.golditemexpansion.block.ModBlocks;
import org.saverio.golditemexpansion.item.ModItems;

public class GolditemexpansionClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register(goldenHeadBlockColor, ModBlocks.GOLDEN_HEAD_BLOCK);
        ColorProviderRegistry.ITEM.register(goldenHeadItemColor, ModItems.GOLDEN_HEAD_ITEM);
    }
}
