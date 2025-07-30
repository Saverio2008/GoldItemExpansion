package org.saverio.golditemexpansion.fabric;

import org.saverio.golditemexpansion.Golditemexpansion;
import net.fabricmc.api.ModInitializer;
import org.saverio.golditemexpansion.fabric.registry.block.ModBlocks;
import org.saverio.golditemexpansion.fabric.registry.effect.ModEffects;
import org.saverio.golditemexpansion.fabric.registry.item.ModItems;
import org.saverio.golditemexpansion.fabric.registry.potion.ModPotions;

public final class GolditemexpansionFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEffects.registerEffects();
        ModBlocks.registerBlocks();
        ModPotions.registerPotions();
        ModItems.registerItems();
        Golditemexpansion.init();
    }
}
