package org.saverio.golditemexpansion.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.saverio.golditemexpansion.Golditemexpansion;

@Mod(Golditemexpansion.MOD_ID)
public final class GolditemexpansionForge {
    public GolditemexpansionForge() {
        EventBuses.registerModEventBus(Golditemexpansion.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Golditemexpansion.init();
    }
}
