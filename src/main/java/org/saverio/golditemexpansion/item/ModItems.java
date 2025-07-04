package org.saverio.golditemexpansion.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.block.ModBlocks;

public class ModItems {
    public static void registerItems() {
        Registry.register(Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "compressed_gold_block"),
                new BlockItem(ModBlocks.COMPRESSED_GOLD_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS))
        );
    }
}