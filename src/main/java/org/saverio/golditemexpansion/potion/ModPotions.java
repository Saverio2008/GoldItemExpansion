package org.saverio.golditemexpansion.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.effect.ModEffects;

public class ModPotions {
    public static Potion GOD_POTION;

    public static void registerPotions() {
        GOD_POTION = Registry.register(
                Registries.POTION,
                new Identifier(Golditemexpansion.MOD_ID, "god_potion"),
                new Potion(
                        new StatusEffectInstance(ModEffects.GOD_STATUS_EFFECT, 3600, 0)
                )
        );
        ModBrewingRecipes.registerBrewingRecipes();
    }
}