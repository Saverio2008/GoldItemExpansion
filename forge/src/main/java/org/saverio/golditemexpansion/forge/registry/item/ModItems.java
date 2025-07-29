package org.saverio.golditemexpansion.forge.registry.item;

import net.minecraft.world.item.*;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
public final class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> COMPRESSED_GOLD_BLOCK_ITEM = ITEMS.register("compressed_gold_block", () ->
            new Item(new Item.Properties())
    );

    public static void registerItems(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @SubscribeEvent
    public static void addItemsToTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
            for (var entry : entries) {
                ItemStack existing = entry.getKey();
                if (existing.getItem() == Items.GOLD_BLOCK) {
                    entries.putAfter(existing, new ItemStack(COMPRESSED_GOLD_BLOCK_ITEM.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    break;
                }
            }
        }
    }
}