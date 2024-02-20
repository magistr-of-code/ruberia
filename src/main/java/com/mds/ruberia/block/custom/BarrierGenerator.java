package com.mds.ruberia.block.custom;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.effects.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.random.RandomGenerator;

public class BarrierGenerator extends Block {

    public BarrierGenerator(Settings settings) {
        super(settings);
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

        ModEffects.ShockWave(world,pos.toCenterPos().add(0,1,0),16,null);

        world.setBlockState(pos.add(0,1,0),ModBlocks.PURPLE_CRYSTAL.getDefaultState());

        for(int g = 1; g != RandomGenerator.getDefault().nextInt(5,9); g++) {
            for (int i = 0; i != 360; i++) {
                double cos = Math.cos(Math.toDegrees(i));
                double sin = Math.sin(Math.toDegrees(i));

                if (RandomGenerator.getDefault().nextBoolean()){
                    Vec3d crystalPos = pos.toCenterPos().add((cos*2)/g,g-1,(sin*2)/g);

                    if(world.canSetBlock(BlockPos.ofFloored(crystalPos))){
                        world.setBlockState(BlockPos.ofFloored(crystalPos), ModBlocks.PURPLE_CRYSTAL.getDefaultState());
                    }
                }
            }
        }

        super.onPlaced(world, pos, state, placer, itemStack);
    }
}
