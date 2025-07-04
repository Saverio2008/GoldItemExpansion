package org.saverio.golditemexpansion;

import org.saverio.golditemexpansion.item.ModItems;
import net.fabricmc.api.ModInitializer;

public class Golditemexpansion implements ModInitializer {
    public static final String MOD_ID = "golditemexpansion";
    @Override
    public void onInitialize() {
        ModItems.registerItems();
    }
}