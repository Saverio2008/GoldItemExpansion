package org.saverio.golditemexpansion.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.saverio.golditemexpansion.block.ModBlocks;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

public final class ModItems {
    private ModItems() {}
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(MOD_ID, net.minecraft.core.registries.Registries.ITEM);
    public static final RegistrySupplier<Item> COMPRESSED_GOLD_BLOCK_ITEM =
            ITEMS.register("compressed_gold_block", () ->
                    new BlockItem(ModBlocks.COMPRESSED_GOLD_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> GOLDEN_HEAD_BLOCK_ITEM =
            ITEMS.register("golden_head", () ->
                    new BlockItem(ModBlocks.GOLDEN_HEAD_BLOCK.get(), new Item.Properties()));
}