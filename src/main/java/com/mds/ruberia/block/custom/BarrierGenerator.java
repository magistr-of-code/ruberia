package com.mds.ruberia.block.custom;

import com.mds.ruberia.effects.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.random.RandomGenerator;

public class BarrierGenerator extends Block {

    public BarrierGenerator(Settings settings) {
        super(settings);
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

        if(RandomGenerator.getDefault().nextBoolean()){
            ModEffects.ShockWave(world,pos.toCenterPos().add(0,1,0),16,null);
        } else {
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT,world);
            lightning.setPosition(pos.toCenterPos());
            world.spawnEntity(lightning);
            world.createExplosion(lightning,pos.getX(),pos.getY(),pos.getZ(),4, World.ExplosionSourceType.BLOCK);
        }



        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player){

        player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA));

        if(RandomGenerator.getDefault().nextBoolean()){
            ModEffects.ShockWave(world,pos.toCenterPos().add(0,1,0),16,null);
        } else {
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT,world);
            lightning.setPosition(pos.toCenterPos());
            world.spawnEntity(lightning);
            world.createExplosion(lightning,pos.getX(),pos.getY(),pos.getZ(),4, World.ExplosionSourceType.BLOCK);
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {

        LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT,world);
        lightning.setPosition(pos.toCenterPos());
        world.spawnEntity(lightning);
        world.createExplosion(lightning,pos.getX(),pos.getY(),pos.getZ(),4, World.ExplosionSourceType.BLOCK);

        super.onDestroyedByExplosion(world, pos, explosion);
    }
}
