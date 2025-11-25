package org.saverio.golditemexpansion.neoforge.events;

import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.item.ModItems;

@EventBusSubscriber(modid = Golditemexpansion.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class CreativeTabEvents {
    @SubscribeEvent
    public static void addItemsToTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            insertAfter(event, Items.GOLD_BLOCK, new ItemStack(ModItems.COMPRESSED_GOLD_BLOCK_ITEM.get()));
        }
        if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            insertAfter(event, Items.DRAGON_HEAD, new ItemStack(ModItems.GOLDEN_HEAD_BLOCK_ITEM.get()));
        }
    }
    private static void insertAfter(BuildCreativeModeTabContentsEvent event,
                                    Item targetItem, ItemStack toInsert) {
        var entries = event.getEntries();
        ItemStack targetStack = null;
        for (var entry : entries) {
            if (entry.getKey().is(targetItem)) {
                if (entry.getKey().is(toInsert.getItem())) return;
                targetStack = entry.getKey();
                break;
            }
        }
        if (targetStack == null) return;
        entries.putAfter(targetStack, toInsert, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
}
