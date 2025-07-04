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
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(new LanguageProvider(pack));
        pack.addProvider(new RecipeProvider(pack));
        pack.addProvider(new ModelProvider(pack));
    }

    private static class LanguageProvider extends FabricLanguageProvider {
        public LanguageProvider(FabricDataGenerator.Pack pack) {
            super(pack);
        }

        @Override
        public void generateTranslations(TranslationBuilder translationBuilder) {
            translationBuilder.add(ModItems.COMPRESSED_GOLD_BLOCK, "压缩金块");
        }
    }

    private static class RecipeProvider extends FabricRecipeProvider {
        public RecipeProvider(FabricDataGenerator.Pack pack) {
            super(pack);
        }

        @Override
        public void generate(Consumer<RecipeJsonProvider> exporter) {
            offerShapedRecipe(exporter, ModItems.COMPRESSED_GOLD_BLOCK,
                    "GGG",
                    "GGG",
                    "GGG",
                    'G', Items.GOLD_BLOCK);
        }
    }

    private static class ModelProvider extends FabricModelProvider {
        public ModelProvider(FabricDataGenerator.Pack pack) {
            super(pack);
        }

        @Override
        public void generateBlockStateModels() {
            // 此处不需要方块模型（非方块物品）
        }

        @Override
        public void generateItemModels() {
            itemModelGenerated(ModItems.COMPRESSED_GOLD_BLOCK);
        }
    }
}