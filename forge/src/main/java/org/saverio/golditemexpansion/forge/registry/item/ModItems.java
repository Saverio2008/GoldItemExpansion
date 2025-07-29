package org.saverio.golditemexpansion.forge.registry.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.block.CompressedGoldBlock;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Golditemexpansion.MOD_ID);

    // 注册压缩金块的物品表示
    public static final RegistryObject<Item> COMPRESSED_GOLD_BLOCK_ITEM =
            ITEMS.register("compressed_gold_block", () ->
                    new BlockItem(CompressedGoldBlock.COMPRESSED_GOLD_BLOCK, new Item.Properties())
            );
    public static void registerItems(IEventBus bus) {
        ITEMS.register(bus);
    }
}
