package org.saverio.golditemexpansion.potion;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import org.saverio.golditemexpansion.effect.ModEffects;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

public final class ModPotions {
    private ModPotions() {}
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(MOD_ID, Registries.POTION);
    public static final RegistrySupplier<Potion> GOD_POTION =
            POTIONS.register("god_potion", () ->
                    new Potion(new MobEffectInstance(ModEffects.godPlayerHolder(), 3600, 0)));
    public static final RegistrySupplier<Potion> UNDEAD_GOD_POTION =
            POTIONS.register("undead_god_potion", () ->
                    new Potion(new MobEffectInstance(ModEffects.godUndeadHolder(), 3600, 1)));
    public static final RegistrySupplier<Potion> ARTHROPOD_GOD_POTION =
            POTIONS.register("arthropod_god_potion", () ->
                    new Potion(new MobEffectInstance(ModEffects.godArthropodHolder(), 3600, 2)));
    public static final RegistrySupplier<Potion> HEALING_III =
            POTIONS.register("healing_iii", () ->
                    new Potion(new MobEffectInstance(MobEffects.HEAL, 1, 2)));
    public static final RegistrySupplier<Potion> GODLY_HEALING =
            POTIONS.register("godly_healing", () ->
                    new Potion(new MobEffectInstance(MobEffects.HEAL, 1, 4)));
}