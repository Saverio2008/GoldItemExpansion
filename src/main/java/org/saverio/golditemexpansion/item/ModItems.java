package org.saverio.golditemexpansion.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
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

    public static final ItemStack GOD_SPLASH_POTION_STACK = PotionUtil.setPotion(
            new ItemStack(Items.SPLASH_POTION), ModPotions.GOD_POTION);

    public static final ItemStack GOD_LINGERING_POTION_STACK = PotionUtil.setPotion(
            new ItemStack(Items.LINGERING_POTION), ModPotions.GOD_POTION);

    public static final ItemStack HEALING_V_STACK = PotionUtil.setPotion(
            new ItemStack(Items.POTION), ModPotions.HEALING_V);

    public static final ItemStack GODLY_HEALING_STACK = PotionUtil.setPotion(
            new ItemStack(Items.POTION), ModPotions.GODLY_HEALING);

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

            ItemStack strongHealingStack = null;
            for (ItemStack stack : content.getSearchTabStacks()) {
                if (stack.getItem() == Items.POTION && PotionUtil.getPotion(stack) == Potions.STRONG_HEALING) {
                    strongHealingStack = stack;
                    break;
                }
            }
            if (strongHealingStack != null && ItemStack.canCombine(strongHealingStack, HEALING_V_STACK)) {
                content.addAfter(strongHealingStack, HEALING_V_STACK);
            } else {
                content.add(HEALING_V_STACK);
            }

            content.addAfter(HEALING_V_STACK, GODLY_HEALING_STACK);
        });
    }
}