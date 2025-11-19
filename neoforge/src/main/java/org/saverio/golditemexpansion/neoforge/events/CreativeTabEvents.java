package org.saverio.golditemexpansion.neoforge.events;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.item.ModItems;

import java.util.ArrayList;

@EventBusSubscriber(modid = Golditemexpansion.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class CreativeTabEvents {
    @SubscribeEvent
    public static void addItemsToTab(BuildCreativeModeTabContentsEvent event) {
        insertAfter(event, Items.GOLD_BLOCK, new ItemStack(ModItems.COMPRESSED_GOLD_BLOCK_ITEM.get()));
        insertAfter(event, Items.DRAGON_HEAD, new ItemStack(ModItems.GOLDEN_HEAD_BLOCK_ITEM.get()));
    }
    private static void insertAfter(
            BuildCreativeModeTabContentsEvent event,
            net.minecraft.world.item.Item targetItem,
            ItemStack toInsert
    ) {
        var entries = event.getEntries();
        var targetStacks = new ArrayList<ItemStack>();
        for (var entry : entries) {
            if (entry.getKey().getItem() == targetItem) {
                targetStacks.add(entry.getKey());
            }
        }
        if (targetStacks.isEmpty()) return;
        for (ItemStack targetStack : targetStacks) {
            entries.putAfter(targetStack, toInsert, net.minecraft.world.item.CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
