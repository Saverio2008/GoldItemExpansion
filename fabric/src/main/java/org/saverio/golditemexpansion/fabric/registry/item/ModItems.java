package org.saverio.golditemexpansion.fabric.registry.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;
import static org.saverio.golditemexpansion.item.CompressedGoldBlockItem.COMPRESSED_GOLD_BLOCK_ITEM;

public final class ModItems {
    public static void registerItems() {
        Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK_ITEM
        );
    }
}