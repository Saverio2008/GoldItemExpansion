package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;

import java.util.Set;

public class ModEffects {
    public static final GodStatusEffect GOD_STATUS_EFFECT = new GodStatusEffect();

    public static final Set<StatusEffect> HIDDEN_EFFECTS = Set.of(
            StatusEffects.SPEED,
            StatusEffects.HASTE,
            StatusEffects.STRENGTH,
            StatusEffects.JUMP_BOOST,
            StatusEffects.REGENERATION,
            StatusEffects.RESISTANCE,
            StatusEffects.FIRE_RESISTANCE,
            StatusEffects.WATER_BREATHING,
            StatusEffects.INVISIBILITY,
            StatusEffects.NIGHT_VISION,
            StatusEffects.HEALTH_BOOST,
            StatusEffects.ABSORPTION,
            StatusEffects.LUCK,
            StatusEffects.SLOW_FALLING,
            StatusEffects.CONDUIT_POWER,
            StatusEffects.DOLPHINS_GRACE,
            StatusEffects.HERO_OF_THE_VILLAGE
    );

    public static void registerEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Golditemexpansion.MOD_ID, "god_status_effect"), GOD_STATUS_EFFECT);
    }
}