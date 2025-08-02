package org.saverio.golditemexpansion.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public final class BrewingRecipeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger("GoldItemExpansion");

    public static void addMix(Potion from, Item ingredient, Potion to) {
        try {
            Method addMix = PotionBrewing.class.getDeclaredMethod("addMix", Potion.class, Item.class, Potion.class);
            addMix.setAccessible(true);
            addMix.invoke(null, from, ingredient, to);

            String fromId = BuiltInRegistries.POTION.getKey(from).toString();
            String toId = BuiltInRegistries.POTION.getKey(to).toString();
            String itemId = BuiltInRegistries.ITEM.getKey(ingredient).toString();

            LOGGER.debug("Registered brewing recipe: {} + {} → {}", fromId, itemId, toId);
        } catch (Exception e) {
            LOGGER.error("Failed to register brewing recipe", e);
        }
    }
}