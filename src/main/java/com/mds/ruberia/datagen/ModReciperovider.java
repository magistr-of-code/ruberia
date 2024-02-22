package com.mds.ruberia.datagen;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModReciperovider extends FabricRecipeProvider {

    private static final List<ItemConvertible> COAL_COKE_SMELTABLES = List.of(Items.COAL);

    public ModReciperovider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter,COAL_COKE_SMELTABLES, RecipeCategory.MISC, ModItems.COAL_COKE,0.7f,200,"ingredients");
        offerBlasting(exporter,COAL_COKE_SMELTABLES, RecipeCategory.MISC, ModItems.COAL_COKE,0.7f,200,"ingredients");

        offerReversibleCompactingRecipes(exporter,RecipeCategory.BUILDING_BLOCKS,ModItems.FLARIUM_INGOT,RecipeCategory.DECORATIONS, ModBlocks.FLARIUM_BLOCK);
        offerCompactingRecipe(exporter,RecipeCategory.BUILDING_BLOCKS,ModItems.BARRIER_CRYSTAL,ModBlocks.BARRIER_GENERATOR);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC,ModItems.AMETHYST_STAFF,1)
                .pattern("  $")
                .pattern(" C ")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('C', ModBlocks.PURPLE_CRYSTAL)
                .input('$', Items.AMETHYST_SHARD)
                .criterion(hasItem(ModBlocks.PURPLE_CRYSTAL),conditionsFromItem(ModBlocks.PURPLE_CRYSTAL))
                .criterion(hasItem(Items.AMETHYST_SHARD),conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.AMETHYST_STAFF)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC,ModItems.BARRIER_NECKLACE,1)
                .pattern(" S ")
                .pattern("S S")
                .pattern(" C ")
                .input('S', Items.STRING)
                .input('C', ModItems.BARRIER_CRYSTAL)
                .criterion(hasItem(ModItems.BARRIER_CRYSTAL),conditionsFromItem(ModItems.BARRIER_CRYSTAL))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.BARRIER_NECKLACE)));
    }
}
