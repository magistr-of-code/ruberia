package com.mds.ruberia.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlariumBarrierBlockEntity extends BlockEntity {
    protected final PropertyDelegate propertyDelegate;
    private int lifeTime = 0;

    public FlariumBarrierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLARIUM_BARRIER_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> FlariumBarrierBlockEntity.this.lifeTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> FlariumBarrierBlockEntity.this.lifeTime = value;
                }
            }

            @Override
            public int size() {
                return 1;
            }
        };
    }

    public void tick(World world1, BlockPos pos, BlockState state1) {
        lifeTime++;

        if (lifeTime>=240){
            world1.breakBlock(pos,false);
            world1.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }
}
