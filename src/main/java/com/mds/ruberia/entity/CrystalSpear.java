package com.mds.ruberia.entity;

import com.mds.ruberia.effects.ModEffects;
import com.mds.ruberia.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class CrystalSpear extends ThrownItemEntity {
    public CrystalSpear(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public CrystalSpear(World world, LivingEntity owner) {
        super(ModEntities.CRYSTAL_SPEAR_ENTITY, owner, world);
    }

    public CrystalSpear(World world, double x, double y, double z) {
        super(ModEntities.CRYSTAL_SPEAR_ENTITY, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.AMETHYST_STAFF;
    }



    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {

        ModEffects.ShockWave(this.getWorld(),this.getPos().add(0,1,0),8,null);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        entity.getWorld().playSound(entity.getPos().getX(),entity.getPos().getY(),entity.getPos().getZ(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.AMBIENT, 2F, 1F,true);
        entity.damage(entity.getDamageSources().magic(),5f);

        if (entity instanceof LivingEntity livingEntity) {
            //nothing
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {

            this.getWorld().playSound(this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.AMBIENT, 2F, 1F,true);
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.kill();
        }

    }
}