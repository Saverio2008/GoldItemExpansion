package org.saverio.golditemexpansion.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
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

    public static void registerPotions() {
        // 注册 Potion 实例
        GOD_POTION = Registry.register(
                Registries.POTION,
                new Identifier(Golditemexpansion.MOD_ID, "god_potion"),
                new Potion(new StatusEffectInstance(ModEffects.GOD_STATUS_EFFECT, 3600, 0,false,true,false))
        );
        // 注册药水物品
        GOD_POTION_ITEM = Registry.register(
                Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "god_potion"),
                new PotionItem(new Item.Settings().rarity(Rarity.RARE))
        );
        ModBrewingRecipes.registerBrewingRecipes();
    }
}