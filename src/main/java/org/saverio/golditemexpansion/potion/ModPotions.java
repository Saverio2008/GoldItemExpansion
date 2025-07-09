package org.saverio.golditemexpansion.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;

public class ModPotions {
    public static Potion GOD_POTION;

    public static void registerPotions() {
        GOD_POTION = Registry.register(
                Registries.POTION,
                new Identifier(Golditemexpansion.MOD_ID, "god_potion"),
                new Potion(
                        new StatusEffectInstance(StatusEffects.STRENGTH, 3600, 4),
                        new StatusEffectInstance(StatusEffects.RESISTANCE, 3600, 4),
                        new StatusEffectInstance(StatusEffects.REGENERATION, 3600, 4),
                        new StatusEffectInstance(StatusEffects.SPEED, 3600, 4),
                        new StatusEffectInstance(StatusEffects.HASTE, 3600, 4),
                        new StatusEffectInstance(StatusEffects.JUMP_BOOST, 3600, 4),
                        new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 3600, 4),
                        new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 3600, 0),
                        new StatusEffectInstance(StatusEffects.WATER_BREATHING, 3600, 0)
                )
        );
        ModBrewingRecipes.registerBrewingRecipes();
    }
}