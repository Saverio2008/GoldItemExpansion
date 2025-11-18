package org.saverio.golditemexpansion.forge.registry.potion;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.forge.registry.item.ModItems;
import org.saverio.golditemexpansion.potion.ModPotionInstances;

import java.util.function.Supplier;

public final class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, Golditemexpansion.MOD_ID);

    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> GOD_POTION =
            registerPotion("god_potion", () -> ModPotionInstances.GOD_POTION);
    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> UNDEAD_GOD_POTION =
            registerPotion("undead_god_potion", () -> ModPotionInstances.UNDEAD_GOD_POTION);
    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> ARTHROPOD_GOD_POTION =
            registerPotion("arthropod_god_potion", () -> ModPotionInstances.ARTHROPOD_GOD_POTION);
    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> HEALING_III =
            registerPotion("healing_iii", () -> ModPotionInstances.HEALING_III);
    @SuppressWarnings("unused")
    public static final RegistryObject<Potion> GODLY_HEALING =
            registerPotion("godly_healing", () -> ModPotionInstances.GODLY_HEALING);

    private static RegistryObject<Potion> registerPotion(String name, Supplier<Potion> supplier) {
        return POTIONS.register(name, supplier);
    }

    public static void registerBrewingRecipes(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(
                    Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                    Ingredient.of(Items.GOLDEN_APPLE),
                    PotionUtils.setPotion(new ItemStack(Items.POTION), HEALING_III.get())
            );
            BrewingRecipeRegistry.addRecipe(
                    Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                    Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE),
                    PotionUtils.setPotion(new ItemStack(Items.POTION), GODLY_HEALING.get())
            );
            BrewingRecipeRegistry.addRecipe(
                    Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), GOD_POTION.get())),
                    Ingredient.of(Items.ROTTEN_FLESH),
                    PotionUtils.setPotion(new ItemStack(Items.POTION), UNDEAD_GOD_POTION.get())
            );
            BrewingRecipeRegistry.addRecipe(
                    Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), GOD_POTION.get())),
                    Ingredient.of(Items.SPIDER_EYE),
                    PotionUtils.setPotion(new ItemStack(Items.POTION), ARTHROPOD_GOD_POTION.get())
            );
            BrewingRecipeRegistry.addRecipe(
                    Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)),
                    Ingredient.of(ModItems.GOLDEN_HEAD_BLOCK_ITEM.get()),
                    PotionUtils.setPotion(new ItemStack(Items.POTION), GOD_POTION.get())
            );
        });
    }
}
