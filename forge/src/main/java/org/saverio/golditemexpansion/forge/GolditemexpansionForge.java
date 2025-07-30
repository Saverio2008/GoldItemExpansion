package org.saverio.golditemexpansion.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.forge.registry.block.ModBlocks;
import org.saverio.golditemexpansion.forge.registry.effect.ModEffects;
import org.saverio.golditemexpansion.forge.registry.item.ModItems;
import org.saverio.golditemexpansion.forge.registry.potion.ModPotions;

@Mod(Golditemexpansion.MOD_ID)
public final class GolditemexpansionForge {
    public GolditemexpansionForge() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Golditemexpansion.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ModEffects.registerEffects(modEventBus);
        ModBlocks.registerBlocks(modEventBus);
        ModPotions.registerPotions();
        ModItems.registerItems(modEventBus);
        Golditemexpansion.init();
    }
}
