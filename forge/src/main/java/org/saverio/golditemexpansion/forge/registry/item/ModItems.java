package org.saverio.golditemexpansion.forge.registry.item;

import net.minecraft.world.item.*;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.saverio.golditemexpansion.forge.registry.block.ModBlocks;

import java.util.ArrayList;
import java.util.List;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
public final class ModItems {
    public static final DeferredRegister<Item>
            ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> COMPRESSED_GOLD_BLOCK_ITEM =
            ITEMS.register("compressed_gold_block", () ->
                    new BlockItem(ModBlocks.COMPRESSED_GOLD_BLOCK.get(), new Item.Properties())
            );
    public static final RegistryObject<Item> GOLDEN_HEAD_BLOCK_ITEM = ITEMS.register("golden_head", () ->
            new BlockItem(ModBlocks.GOLDEN_HEAD_BLOCK.get(), new Item.Properties())
    );

    @SubscribeEvent
    public static void addItemsToTab(BuildCreativeModeTabContentsEvent event) {
        insertAfter(event, Items.GOLD_BLOCK, new ItemStack(COMPRESSED_GOLD_BLOCK_ITEM.get()));
        insertAfter(event, Items.DRAGON_HEAD, new ItemStack(GOLDEN_HEAD_BLOCK_ITEM.get()));
    }

    private static void insertAfter(
            BuildCreativeModeTabContentsEvent event,
            Item targetItem,
            ItemStack toInsert
    ) {
        MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
        List<ItemStack> targetStacks = new ArrayList<>();
        for (var entry : entries) {
            if (entry.getKey().getItem() == targetItem) {
                targetStacks.add(entry.getKey());
            }
        }
        if (targetStacks.isEmpty()) {
            return;
        }
        for (ItemStack targetStack : targetStacks) {
            entries.putAfter(targetStack, toInsert, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}