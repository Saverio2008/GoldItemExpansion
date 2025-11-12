package org.saverio.golditemexpansion;

import org.saverio.golditemexpansion.block.ModBlocks;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.saverio.golditemexpansion.item.ModItems;
import org.saverio.golditemexpansion.potion.ModPotions;

public final class Golditemexpansion {
    public static final String MOD_ID = "golditemexpansion";

    public static void init() {
        ModEffects.registerEffects();
        ModBlocks.registerBlocks();
        ModPotions.registerPotions();
        ModItems.registerItems();
    }
}
