package org.saverio.golditemexpansion.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.block.ModBlocks;

public class ModItems {
    public static void registerItems() {
        // 注册能放置方块的物品（BlockItem）
        Registry.register(Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "compressed_gold_block"),
                new BlockItem(ModBlocks.COMPRESSED_GOLD_BLOCK, new FabricItemSettings()));
    }
}