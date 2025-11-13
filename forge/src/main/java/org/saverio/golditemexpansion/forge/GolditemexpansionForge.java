package org.saverio.golditemexpansion.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.IEventBus;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.forge.registry.block.ModBlocks;
import org.saverio.golditemexpansion.forge.registry.effect.ModEffects;
import org.saverio.golditemexpansion.forge.registry.item.ModItems;
import org.saverio.golditemexpansion.forge.registry.potion.ModPotions;

@Mod(Golditemexpansion.MOD_ID)
public final class GolditemexpansionForge {
    public GolditemexpansionForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Golditemexpansion.MOD_ID, modEventBus);
        ModEffects.registerEffects(modEventBus);
        ModBlocks.registerBlocks(modEventBus);
        ModPotions.registerPotions(modEventBus);
        ModItems.registerItems(modEventBus);
        modEventBus.addListener(ModPotions::onCommonSetup);
        Golditemexpansion.init();
    }
}
