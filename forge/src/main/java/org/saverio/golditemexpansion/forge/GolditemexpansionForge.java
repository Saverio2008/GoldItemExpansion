package org.saverio.golditemexpansion.forge;

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
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModEffects.EFFECTS.register(modBus);
        ModPotions.POTIONS.register(modBus);
        modBus.addListener(ModPotions::registerBrewingRecipes);
        Golditemexpansion.init();
    }
}
