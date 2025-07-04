package org.saverio.golditemexpansion.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;
import org.saverio.golditemexpansion.item.ModItems;

import java.util.function.Consumer;

public class GolditemexpansionDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.getModContainer(new LanguageProvider(fabricDataGenerator));
        fabricDataGenerator.addProvider(new RecipeProvider(fabricDataGenerator));
        fabricDataGenerator.addProvider(new ModelProvider(fabricDataGenerator));
    }

    private static class LanguageProvider extends FabricLanguageProvider {
        public LanguageProvider(FabricDataGenerator generator) {
            super(generator);
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            translationBuilder.add(ModItems.GOLD_STAFF, "Gold Staff");
        }
    }

    private static class RecipeProvider extends FabricRecipeProvider {
        public RecipeProvider(FabricDataGenerator generator) {
            super(generator);
        }

        @Override
        public void generateRecipes() {
            offerShapedRecipe(ModItems.GOLD_STAFF,
                    " G ",
                    " S ",
                    " S ",
                    'G', Items.GOLD_INGOT,
                    'S', Items.STICK);
        }
    }

    private static class ModelProvider extends FabricModelProvider {
        public ModelProvider(FabricDataGenerator generator) {
            super(generator);
        }

        @Override
        public void generateBlockStateModels() {
            // 空实现
        }

        @Override
        public void generateItemModels() {
            itemModelGenerated(ModItems.GOLD_STAFF);
        }
    }
}