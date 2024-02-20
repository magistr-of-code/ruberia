package com.mds.ruberia.entity;

import com.mds.ruberia.Ruberia;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<CrystalSpear> CRYSTAL_SPEAR_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Ruberia.MOD_ID, "crystal_spear"),
            FabricEntityTypeBuilder.<CrystalSpear>create(SpawnGroup.MISC, CrystalSpear::new).dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build()
    );
}
