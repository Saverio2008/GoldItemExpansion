package org.saverio.golditemexpansion.fabric;

import org.saverio.golditemexpansion.Golditemexpansion;
import net.fabricmc.api.ModInitializer;

public final class GolditemexpansionFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Golditemexpansion.init();
    }
}
