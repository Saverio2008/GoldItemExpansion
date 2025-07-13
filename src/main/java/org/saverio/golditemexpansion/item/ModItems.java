package org.saverio.golditemexpansion.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.Golditemexpansion;
import org.saverio.golditemexpansion.block.ModBlocks;
import org.saverio.golditemexpansion.potion.ModPotions;

public class ModItems {
    public static final Item COMPRESSED_GOLD_BLOCK_ITEM =
            new BlockItem(ModBlocks.COMPRESSED_GOLD_BLOCK, new FabricItemSettings());

    public static final Item GOLDEN_HEAD_ITEM =
            new BlockItem(ModBlocks.GOLDEN_HEAD_BLOCK, new FabricItemSettings());

    public static final ItemStack GOD_POTION_STACK = PotionUtil.setPotion(
            new ItemStack(Items.POTION), ModPotions.GOD_POTION);

    public static final ItemStack HEALING_V_STACK = PotionUtil.setPotion(
            new ItemStack(Items.POTION), ModPotions.HEALING_V);

    public static final ItemStack GODLY_HEALING_STACK = PotionUtil.setPotion(
            new ItemStack(Items.POTION), ModPotions.GODLY_HEALING);

    public static final ItemStack GOD_SPLASH_POTION_STACK = PotionUtil.setPotion(
            new ItemStack(Items.SPLASH_POTION), ModPotions.GOD_POTION);

    public static final ItemStack HEALING_V_SPLASH_STACK = PotionUtil.setPotion(
            new ItemStack(Items.SPLASH_POTION), ModPotions.HEALING_V);

    public static final ItemStack GODLY_HEALING_SPLASH_STACK = PotionUtil.setPotion(
            new ItemStack(Items.SPLASH_POTION), ModPotions.GODLY_HEALING);

    public static final ItemStack GOD_LINGERING_POTION_STACK = PotionUtil.setPotion(
            new ItemStack(Items.LINGERING_POTION), ModPotions.GOD_POTION);

    public static final ItemStack HEALING_V_LINGERING_STACK = PotionUtil.setPotion(
            new ItemStack(Items.LINGERING_POTION), ModPotions.HEALING_V);

    public static final ItemStack GODLY_HEALING_LINGERING_STACK = PotionUtil.setPotion(
            new ItemStack(Items.LINGERING_POTION), ModPotions.GODLY_HEALING);

    @SuppressWarnings("UnstableApiUsage")
    public static void registerItems() {
        Registry.register(Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "compressed_gold_block"),
                COMPRESSED_GOLD_BLOCK_ITEM);

        Registry.register(Registries.ITEM,
                new Identifier(Golditemexpansion.MOD_ID, "golden_head"),
                GOLDEN_HEAD_ITEM);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content ->
                content.addAfter(Blocks.GOLD_BLOCK.asItem(), COMPRESSED_GOLD_BLOCK_ITEM));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content ->
                content.addAfter(Blocks.DRAGON_HEAD.asItem(), GOLDEN_HEAD_ITEM));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(content -> {
            content.add(GOD_POTION_STACK);
            content.add(GOD_SPLASH_POTION_STACK);
            content.add(GOD_LINGERING_POTION_STACK);
            content.add(HEALING_V_STACK);
            content.add(HEALING_V_SPLASH_STACK);
            content.add(HEALING_V_LINGERING_STACK);
            content.add(GODLY_HEALING_STACK);
            content.add(GODLY_HEALING_SPLASH_STACK);
            content.add(GODLY_HEALING_LINGERING_STACK);
        });
    }
}