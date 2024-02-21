package com.mds.ruberia.datagen;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.BARRIER_GENERATOR);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUNE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FLARIUM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PURPLE_CRYSTAL);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.COAL_COKE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLARIUM_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.BARRIER_CRYSTAL, Models.GENERATED);
    }
}
