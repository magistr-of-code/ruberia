package com.mds.ruberia.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class LightingStrikeEnchantment extends Enchantment {
    protected LightingStrikeEnchantment(Rarity weight) {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        World world = user.getWorld();

        int power = 4*level;

        List<Entity> Entities = world.getOtherEntities(user,new Box(target.getX()-power,target.getY()-power,target.getZ()-power,target.getX()+power,target.getY()+power,target.getZ()+power));

        for (int i = 0;i<=Entities.toArray().length-1;i++){
            Entity entity = Entities.get(i);
            if (target.isInRange(entity,power)&&entity.isLiving()){

                //world.playSound(null,entity.getX(),entity.getY(),entity.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.AMBIENT,1,2);
                EntityType.LIGHTNING_BOLT.spawn((ServerWorld) world,entity.getBlockPos(),SpawnReason.EVENT);

            }
        }

        super.onTargetDamaged(user, target, level);
    }
}
