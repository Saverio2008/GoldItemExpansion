package org.saverio.golditemexpansion.fabric.registry.effect;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.saverio.golditemexpansion.Golditemexpansion;

import static org.saverio.golditemexpansion.effect.ModEffects.*;

public final class ModEffects {
    public static void registerEffects() {
        Registry.register(BuiltInRegistries.MOB_EFFECT,
                new ResourceLocation(Golditemexpansion.MOD_ID, "god_status_effect"), GOD_STATUS_EFFECT);
        Registry.register(BuiltInRegistries.MOB_EFFECT,
                new ResourceLocation(Golditemexpansion.MOD_ID, "god_positive_effect"), GOD_POSITIVE_EFFECT);
        Registry.register(BuiltInRegistries.MOB_EFFECT,
                new ResourceLocation(Golditemexpansion.MOD_ID, "god_negative_effect"), GOD_NEGATIVE_EFFECT);
    }
}
