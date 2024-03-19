package com.mds.ruberia.block.entity;

import com.mds.ruberia.Ruberia;
import com.mds.ruberia.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<WritingTableBlockEntity> WRITING_TABLE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Ruberia.MOD_ID,"writing_table_be"),
                    FabricBlockEntityTypeBuilder.create(WritingTableBlockEntity::new, ModBlocks.WRITING_DESK).build());


    public static final BlockEntityType<FlariumBarrierBlockEntity> FLARIUM_BARRIER_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Ruberia.MOD_ID,"flarium_barrier_be"),
                    FabricBlockEntityTypeBuilder.create(FlariumBarrierBlockEntity::new, ModBlocks.FLARIUM_BARRIER).build());

    public static void registerBlockEntities(){
        Ruberia.LOGGER.info("Registering Block Entities for " + Ruberia.MOD_ID);
    }
}
