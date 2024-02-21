package com.mds.ruberia.entity;

import com.mds.ruberia.effects.ModEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class CrystalSpear extends ThrownItemEntity {

    @Override
    public void tick() {

        this.getWorld().addParticle(ParticleTypes.END_ROD,this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(),0,0,0);

        super.tick();
    }

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
        return Items.AMETHYST_SHARD;
    }



    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        entity.damage(entity.getDamageSources().magic(),5f);

        if (entity instanceof LivingEntity livingEntity) {
            //nothing
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        List<Entity> exceptions = List.of(Objects.requireNonNull(this.getOwner()),this);

        ModEffects.ShockWave(this.getWorld(),this.getPos(),8,exceptions);

        if (!this.getWorld().isClient) {
            this.getWorld().playSound(this.getPos().getX(),this.getPos().getY(),this.getPos().getZ(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.AMBIENT, 2F, 1F,false);
            this.getWorld().sendEntityStatus(this, (byte)3);
            this.kill();
        }

    }

    @Override
    protected float getGravity() {
        return 0.08f;
    }
}