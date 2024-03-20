package com.mds.ruberia.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.world.World;

public final class PurpleLightningEntity extends LightningEntity {
    public PurpleLightningEntity(EntityType<? extends LightningEntity> entityType, World world) {
        super(entityType, world);
    }
}
