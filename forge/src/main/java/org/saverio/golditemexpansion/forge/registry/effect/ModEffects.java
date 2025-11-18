package org.saverio.golditemexpansion.forge.registry.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.effect.ModEffectInstances;

public final class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Golditemexpansion.MOD_ID);
    @SuppressWarnings("unused")
    public static final RegistryObject<MobEffect> GOD_STATUS_EFFECT =
            EFFECTS.register("god_effect", () -> ModEffectInstances.GOD_STATUS_EFFECT);
    @SuppressWarnings("unused")
    public static final RegistryObject<MobEffect> GOD_POSITIVE_EFFECT =
            EFFECTS.register("god_positive_effect", () -> ModEffectInstances.GOD_POSITIVE_EFFECT);
    @SuppressWarnings("unused")
    public static final RegistryObject<MobEffect> GOD_NEGATIVE_EFFECT =
            EFFECTS.register("god_negative_effect", () -> ModEffectInstances.GOD_NEGATIVE_EFFECT);
}