package com.mds.ruberia.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;
import java.util.random.RandomGenerator;

import static net.minecraft.entity.EntityType.PLAYER;

public class AmethystStaffItem extends Item {
    public AmethystStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT,world);

        if(user.isSneaking()){

            Vec3d ray = user.raycast(20,2.0f,false).getPos();

            user.getItemCooldownManager().set(this, 80);

            int posY = (int) ray.getY();
            while(world.getBlockState(new BlockPos((int) ray.getX(),posY, (int) ray.getZ())) == Blocks.AIR.getDefaultState()) {
                posY-=1;
            }
            world.createExplosion(null,ray.getX(),posY,ray.getZ(),4.0f, World.ExplosionSourceType.NONE);
            entity.setPosition(ray.getX(),posY,ray.getZ());
            world.spawnEntity(entity);
        }

        if(!user.isOnGround() && !user.isSneaking()){

            Vec3d ray = user.raycast(20,2.0f,false).getPos();

            user.addVelocity((ray.getX()-user.getX())/5,(ray.getY()-user.getY())/5,(ray.getZ()-user.getZ())/5);

            user.getItemCooldownManager().set(this, 10);

            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20, 6));
            for(int i = 0; i != 200;i++){
                world.addParticle(ParticleTypes.CLOUD,ray.getX(),ray.getY()-1,ray.getZ(), RandomGenerator.getDefault().nextDouble(-1,1),RandomGenerator.getDefault().nextDouble(-0.5,0.5),RandomGenerator.getDefault().nextDouble(-1,1));
            }
        }

        world.playSound(null,user.getBlockPos(),SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK,SoundCategory.AMBIENT,1f,2f);
        return super.use(world, user, hand);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        Objects.requireNonNull(context.getPlayer()).getItemCooldownManager().set(this, 50);

        Vec3d pos = context.getPlayer().getPos();

        PlayerEntity user = context.getPlayer();

        Vec3d ray = user.raycast(20.0f,1,false).getPos();

        List<Entity> Entities = context.getWorld().getOtherEntities(user,new Box(pos.getX()-4,pos.getY()-4,pos.getZ()-4,pos.getX()+4,pos.getY()+4,pos.getZ()+4));

        for (int i = 0; i != Entities.toArray().length; i++) {

            Entities.get(i).addVelocity((ray.getX()-user.getX())/5,(ray.getY()-user.getY())/5,(ray.getZ()-user.getZ())/5);

        }

        return super.useOnBlock(context);
    }



    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        user.getItemCooldownManager().set(this, 40);

        Vec3d ray = user.raycast(15,2.0f,false).getPos();

        for(int i = 0; i != 200;i++){
            user.getWorld().addParticle(ParticleTypes.END_ROD,ray.getX(),ray.getY(),ray.getZ(), RandomGenerator.getDefault().nextDouble(-2,2),RandomGenerator.getDefault().nextDouble(-2,2),RandomGenerator.getDefault().nextDouble(-2,2));
        }

        entity.addVelocity((ray.getX()-user.getX())/2,(ray.getY()-user.getY())/2,(ray.getZ()-user.getZ())/2);
        entity.damage(user.getDamageSources().magic(),5.0f);

        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        Vec3d pos = target.getPos();

        target.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 15, 6));

        return super.postHit(stack, target, attacker);
    }



}
