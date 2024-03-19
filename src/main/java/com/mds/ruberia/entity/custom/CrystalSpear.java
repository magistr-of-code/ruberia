package com.mds.ruberia.entity.custom;

import com.mds.ruberia.effects.ModEffects;
import com.mds.ruberia.entity.ModEntities;
import com.mds.ruberia.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

public class CrystalSpear extends ThrownItemEntity {

    int s=4;
    int lifeTime=0;

    @Override
    public void tick() {
        super.tick();

        lifeTime++;

        getWorld().addParticle(ParticleTypes.END_ROD,getX(),getY(),getZ(),getVelocity().getX()/5,getVelocity().getY()/5,getVelocity().getZ()/5);

        if (lifeTime>=380&&!getWorld().isClient){
            kill();
        }
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
        return ModItems.BARRIER_CRYSTAL;
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

        List<Entity> exceptions = List.of(Objects.requireNonNull(getOwner()),this);

        ModEffects.ShockWave(getWorld(),hitResult.getPos(),8,exceptions);
        //this.getWorld().playSound(hitResult.getPos().getX(),hitResult.getPos().getY(),hitResult.getPos().getZ(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.AMBIENT, 2F, 1F,false);

        if (!getWorld().isClient) {
            int power = 25;
            List<Entity> Entities = getWorld().getOtherEntities(null,new Box(getX()-power,getY()-power,getZ()-power,getX()+power,getY()+power,getZ()+power));

            for (int i = 0; i != Entities.size(); i++) {
                if (Entities.get(i).isLiving()){
                    LivingEntity livingEntity = (LivingEntity) Entities.get(i);
                    livingEntity.tiltScreen(RandomGenerator.getDefault().nextDouble(0,10)-hitResult.getPos().distanceTo(getOwner().getPos()), RandomGenerator.getDefault().nextDouble(0,10)-hitResult.getPos().distanceTo(getOwner().getPos()));
                }
            }


                if (getOwner() != null) {
                getWorld().sendEntityStatus(this, (byte) 3);
                for (int i=0;i!=s;i++){
                    CrystalSpear crystalSpearEntity = new CrystalSpear(getWorld(), (LivingEntity) getOwner());

                    crystalSpearEntity.setPos(getPos().getX() + RandomGenerator.getDefault().nextDouble(-12, 12),getPos().getY() + RandomGenerator.getDefault().nextDouble(1, 12),getPos().getZ() + RandomGenerator.getDefault().nextDouble(-12, 12));

                    crystalSpearEntity.lookAt(crystalSpearEntity.getCommandSource().getEntityAnchor(), hitResult.getPos());

                    crystalSpearEntity.setVelocity(getOwner(),crystalSpearEntity.getPitch(), crystalSpearEntity.getYaw(), 2F, 3F, 0F);

                    crystalSpearEntity.setS(s-1);

                    getWorld().spawnEntity(crystalSpearEntity);
                }
            }

            kill();
        }
    }

    public final void setS(int s){
        this.s = s;
    }

    @Override
    protected float getGravity() {
        return 0.08f;
    }
}