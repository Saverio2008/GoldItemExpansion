package org.saverio.golditemexpansion.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.effect.ModEffects;

public class ModPotions {
    public static Potion GOD_POTION;
    public static Item GOD_POTION_ITEM;
    public static Potion UNDEAD_GOD_POTION;
    public static Item UNDEAD_GOD_POTION_ITEM;
    public static Potion ARTHROPOD_GOD_POTION;
    public static Item ARTHROPOD_GOD_POTION_ITEM;
    public static Potion HEALING_V;
    public static Item HEALING_V_ITEM;
    public static Potion GODLY_HEALING;
    public static Item GODLY_HEALING_ITEM;

    public static void registerPotions() {
        // 注册药水
        GOD_POTION = Registry.register(
                Registries.POTION,
                new Identifier(Golditemexpansion.MOD_ID, "god_potion"),
                new Potion(new StatusEffectInstance(ModEffects.GOD_STATUS_EFFECT,
                        3600, 0,false,true,false))
        );
        UNDEAD_GOD_POTION = Registry.register(Registries.POTION,
                new Identifier(Golditemexpansion.MOD_ID, "undead_god_potion"),
                new Potion(new StatusEffectInstance(ModEffects.GOD_STATUS_EFFECT,
                        3600, 1,false,true,false))
        );
        ARTHROPOD_GOD_POTION = Registry.register(Registries.POTION,
                new Identifier(Golditemexpansion.MOD_ID, "arthropod_god_potion"),
                new Potion(new StatusEffectInstance(ModEffects.GOD_STATUS_EFFECT,
                        3600, 2,false,true,false))
        );
        HEALING_V = Registry.register(
                Registries.POTION,
                new Identifier(Golditemexpansion.MOD_ID, "strong_healing_v"),
                new Potion(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 4))
        );
        GODLY_HEALING = Registry.register(
                Registries.POTION,
                new Identifier(Golditemexpansion.MOD_ID, "godly_healing"),
                new Potion(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 14))
        );
        // 注册药水物品
        GOD_POTION_ITEM = Registry.register(
                Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "god_potion"),
                new PotionItem(new Item.Settings().rarity(Rarity.RARE))
        );
        UNDEAD_GOD_POTION_ITEM = Registry.register(
                Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "undead_god_potion"),
                new PotionItem(new Item.Settings().rarity(Rarity.RARE))
        );
        ARTHROPOD_GOD_POTION_ITEM = Registry.register(
                Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "arthropod_god_potion"),
                new PotionItem(new Item.Settings().rarity(Rarity.RARE))
        );
        HEALING_V_ITEM = Registry.register(
                Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "strong_healing_v"),
                new PotionItem(new Item.Settings().rarity(Rarity.RARE))
        );
        GODLY_HEALING_ITEM = Registry.register(
                Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "godly_healing"),
                new PotionItem(new Item.Settings().rarity(Rarity.RARE))
        );
        ModBrewingRecipes.registerBrewingRecipes();
    }
}