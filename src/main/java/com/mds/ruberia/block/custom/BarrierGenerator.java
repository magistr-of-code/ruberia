package com.mds.ruberia.block.custom;

import com.mds.ruberia.effects.ModEffects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BarrierGenerator extends Block {

    public BarrierGenerator(Settings settings) {
        super(settings);
    }


    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

        ModEffects.ShockWave(world,pos.toCenterPos().add(0,1,0),16,null);

        super.onPlaced(world, pos, state, placer, itemStack);
    }
}
