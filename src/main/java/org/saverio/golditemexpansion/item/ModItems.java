package org.saverio.golditemexpansion.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;

public class ModItems {
    public static final Item GOLD_STAFF = new Item(new FabricItemSettings());

    public static void registerItems() {
        Registry.register(Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "gold_staff"),
                GOLD_STAFF);
    }
}