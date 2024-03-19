package com.mds.ruberia.block;

import com.mds.ruberia.Ruberia;
import com.mds.ruberia.block.custom.BarrierGenerator;
import com.mds.ruberia.block.custom.FlariumBarrierBlock;
import com.mds.ruberia.block.custom.WritingDeskBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block FLARIUM_BLOCK = registerBlock("flarium_block",
            new Block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK)));
    public static final Block FLARIUM_BARRIER = registerBlock("flarium_barrier",
            new FlariumBarrierBlock(FabricBlockSettings.copyOf(Blocks.BARRIER)));

    public static final Block PURPLE_CRYSTAL = registerBlock("purple_crystal",
            new Block(FabricBlockSettings.copyOf(Blocks.AMETHYST_BLOCK).strength(2).hardness(2).luminance(5).nonOpaque()));

    public static final Block RUNE_BLOCK = registerBlock("rune_block",
            new Block(FabricBlockSettings.create().requiresTool().luminance(8).strength(5.0f, 8.0f).sounds(BlockSoundGroup.STONE).instrument(Instrument.CHIME)));

    public static final Block BARRIER_GENERATOR = registerBlock("barrier_generator",
            new BarrierGenerator(FabricBlockSettings.copyOf(Blocks.GLASS).strength(3).hardness(3).luminance(15).nonOpaque()));

    public static final Block WRITING_DESK = registerBlock("writing_desk",
            new WritingDeskBlock(FabricBlockSettings.copyOf(Blocks.SPRUCE_WOOD).strength(3).hardness(3).nonOpaque()));

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name,block);
        return Registry.register(Registries.BLOCK,new Identifier(Ruberia.MOD_ID, name),block);
    }

    public static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM,new Identifier(Ruberia.MOD_ID,name),
                new BlockItem(block,new FabricItemSettings()));
    }

    public static void registerModBlocks(){
        Ruberia.LOGGER.info("Registering mod blocks for " + Ruberia.MOD_ID);
    }

}
