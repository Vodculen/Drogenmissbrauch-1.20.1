package net.vodculen.drogenmissbrauch.datagen;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.vodculen.drogenmissbrauch.block.ModBlocks;
import net.vodculen.drogenmissbrauch.item.ModItems;

public class ModRecipeProvider extends FabricRecipeProvider {
	public ModRecipeProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generate(Consumer<RecipeJsonProvider> exporter) {
		offerReversibleCompactingRecipes(exporter, RecipeCategory.FOOD, Items.SUGAR, RecipeCategory.FOOD, ModBlocks.SUGAR_BLOCK);

		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.NEBULA)
			.input(Items.AMETHYST_SHARD)
			.input(Items.SUGAR)
			.criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
			.offerTo(exporter)
			;

		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.LEVITARE, 8)
			.input(Items.PHANTOM_MEMBRANE)
			.input(Items.SUGAR, 4)
			.criterion(hasItem(Items.PHANTOM_MEMBRANE), conditionsFromItem(Items.PHANTOM_MEMBRANE))
			.offerTo(exporter)
			;

		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.INCANTATIONUM, 4)
			.input(Items.PUFFERFISH)
			.input(Items.APPLE)
			.input(Items.SUGAR, 4)
			.criterion(hasItem(Items.PUFFERFISH), conditionsFromItem(Items.PUFFERFISH))
			.offerTo(exporter)
			;

		ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GESCHWIND_RUBBLE, 8)
			.input(Items.GRAVEL, 1)
			.input(Items.YELLOW_DYE, 1)
			.input(Items.SUGAR, 3)
			.criterion(hasItem(Items.SUGAR), conditionsFromItem(Items.SUGAR))
			.offerTo(exporter)
			;

		ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModBlocks.FROSTED_FROTH)
			.pattern("###")
			.pattern("#%#")
			.pattern("###")
			.input('#', Items.SUGAR)
			.input('%', Items.CAKE)
			.criterion(hasItem(Items.CAKE), conditionsFromItem(Items.CAKE))
			.offerTo(exporter)
			;

		ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.MARZIPAN_AXEBLADE)
			.pattern("#%#")
			.pattern(" % ")
			.pattern(" | ")
			.input('#', ModBlocks.SUGAR_BLOCK)
			.input('%', Items.SUGAR)
			.input('|', Items.STICK)
			.criterion(hasItem(ModBlocks.SUGAR_BLOCK), conditionsFromItem(ModBlocks.SUGAR_BLOCK))
			.offerTo(exporter)
			;
	}
}
