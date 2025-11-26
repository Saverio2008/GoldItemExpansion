package org.saverio.golditemexpansion.effect;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

@SuppressWarnings("unused")
public final class ModEffects {
    private ModEffects() {}
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(MOD_ID, Registries.MOB_EFFECT);
    public static final RegistrySupplier<MobEffect> GOD_POSITIVE_EFFECT =
            EFFECTS.register("god_positive_effect", GodPositiveStatusEffect::new);
    public static final RegistrySupplier<MobEffect> GOD_NEGATIVE_EFFECT =
            EFFECTS.register("god_negative_effect", GodNegativeStatusEffect::new);
    public static final RegistrySupplier<MobEffect> GOD_PLAYER_EFFECT =
            EFFECTS.register("god_player_effect", GodStatusEffects.Player::new);
    public static final RegistrySupplier<MobEffect> GOD_UNDEAD_EFFECT =
            EFFECTS.register("god_undead_effect", GodStatusEffects.Undead::new);
    public static final RegistrySupplier<MobEffect> GOD_ARTHROPOD_EFFECT =
            EFFECTS.register("god_arthropod_effect", GodStatusEffects.Arthropod::new);
    public static final ResourceKey<MobEffect> GOD_POSITIVE_KEY =
            ResourceKey.create(Registries.MOB_EFFECT,
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "god_positive_effect"));
    public static final ResourceKey<MobEffect> GOD_NEGATIVE_KEY =
            ResourceKey.create(Registries.MOB_EFFECT,
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "god_negative_effect"));
    public static final ResourceKey<MobEffect> GOD_PLAYER_KEY =
            ResourceKey.create(Registries.MOB_EFFECT,
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "god_player_effect"));
    public static final ResourceKey<MobEffect> GOD_UNDEAD_KEY =
            ResourceKey.create(Registries.MOB_EFFECT,
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "god_undead_effect"));
    public static final ResourceKey<MobEffect> GOD_ARTHROPOD_KEY =
            ResourceKey.create(Registries.MOB_EFFECT,
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "god_arthropod_effect"));
    public static Holder<MobEffect> godPositiveHolder() {
        return BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(GOD_POSITIVE_KEY);
    }
    public static Holder<MobEffect> godNegativeHolder() {
        return BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(GOD_NEGATIVE_KEY);
    }
    public static Holder<MobEffect> godPlayerHolder() {
        return BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(GOD_PLAYER_KEY);
    }
    public static Holder<MobEffect> godUndeadHolder() {
        return BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(GOD_UNDEAD_KEY);
    }
    public static Holder<MobEffect> godArthropodHolder() {
        return BuiltInRegistries.MOB_EFFECT.getHolderOrThrow(GOD_ARTHROPOD_KEY);
    }
    public static boolean isGodMainEffect(Holder<MobEffect> effect) {
        ResourceKey<MobEffect> key = effect.unwrapKey().orElse(null);
        if (key == null) return false;
        return key.equals(ModEffects.GOD_PLAYER_KEY)
                || key.equals(ModEffects.GOD_UNDEAD_KEY)
                || key.equals(ModEffects.GOD_ARTHROPOD_KEY);
    }
}