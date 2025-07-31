package org.saverio.golditemexpansion.forge.registry.potion;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.potion.ModPotionInstances;

import java.util.function.Supplier;

public final class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, Golditemexpansion.MOD_ID);

    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> GOD_POTION = registerPotion("god", () -> ModPotionInstances.GOD_POTION);
    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> UNDEAD_GOD_POTION = registerPotion("undead_god", () -> ModPotionInstances.UNDEAD_GOD_POTION);
    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> ARTHROPOD_GOD_POTION = registerPotion("arthropod_god", () -> ModPotionInstances.ARTHROPOD_GOD_POTION);
    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> HEALING_V = registerPotion("healing_v", () -> ModPotionInstances.HEALING_V);
    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> GODLY_HEALING = registerPotion("godly_healing", () -> ModPotionInstances.GODLY_HEALING);

    public static void registerPotions(IEventBus bus) {
        POTIONS.register(bus);
    }

    private static RegistryObject<Potion> registerPotion(String name, Supplier<Potion> supplier) {
        return POTIONS.register(name, supplier);
    }
}
