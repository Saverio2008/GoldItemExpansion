package org.saverio.golditemexpansion;

import org.saverio.golditemexpansion.item.ModItems;
import org.saverio.golditemexpansion.block.ModBlocks;
import org.saverio.golditemexpansion.potion.ModPotions;
import org.saverio.golditemexpansion.effect.ModEffects;
import net.fabricmc.api.ModInitializer;

public class Golditemexpansion implements ModInitializer {
    public static final String MOD_ID = "golditemexpansion";
    @Override
    public void onInitialize() {
        ModEffects.registerEffects();
        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModPotions.registerPotions();
    }
}