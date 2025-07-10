package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;

public class ModEffects {
    public static final GodPositiveStatusEffect GOD_POSITIVE_EFFECT = new GodPositiveStatusEffect();
    public static final GodNegativeStatusEffect GOD_NEGATIVE_EFFECT = new GodNegativeStatusEffect();
    public static final GodStatusEffect GOD_STATUS_EFFECT = new GodStatusEffect(StatusEffectCategory.NEUTRAL, 0x000000);

    public static void registerEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Golditemexpansion.MOD_ID, "god_positive_status_effect"), GOD_POSITIVE_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(Golditemexpansion.MOD_ID, "god_negative_status_effect"), GOD_NEGATIVE_EFFECT);
        Registry.register(Registries.STATUS_EFFECT,
                new Identifier(Golditemexpansion.MOD_ID, "god_status_effect"),
                GOD_STATUS_EFFECT);
    }
}