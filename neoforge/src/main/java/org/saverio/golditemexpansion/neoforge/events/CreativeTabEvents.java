package org.saverio.golditemexpansion.neoforge.events;

import net.minecraft.world.item.*;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.saverio.golditemexpansion.item.ModItems;

import java.util.Objects;

public final class CreativeTabEvents {
    private CreativeTabEvents() {}
    public static void register() {
        Objects.requireNonNull(ModLoadingContext.get().getActiveContainer().getEventBus())
                .addListener(BuildCreativeModeTabContentsEvent.class, CreativeTabEvents::addItemsToTab);
    }
    private static void addItemsToTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            event.insertAfter(new ItemStack(Items.GOLD_BLOCK),
                    new ItemStack(ModItems.COMPRESSED_GOLD_BLOCK_ITEM.get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
        if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            event.insertAfter(new ItemStack(Items.DRAGON_HEAD),
                    new ItemStack(ModItems.GOLDEN_HEAD_BLOCK_ITEM.get()),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
