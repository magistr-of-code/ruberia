package com.mds.ruberia.item.custom;

import com.mds.ruberia.effects.ModEffects;
import com.mds.ruberia.entity.CrystalSpear;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AmethystStaffItem extends Item {
    public AmethystStaffItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        Vec3d userPos = user.getPos();

        if (!user.isSneaking()) {
            if (!world.isClient) {
                CrystalSpear crystalSpearEntity = new CrystalSpear(world, user);
                crystalSpearEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 4F, 0F);
                world.spawnEntity(crystalSpearEntity);
            }
        }

        if(user.isSneaking() && !user.isOnGround()) {

            Vec3d raycastPos = user.raycast(10,2.0f,false).getPos();

            double velX = (raycastPos.getX()-userPos.getX())/7;
            double velY = (raycastPos.getY()-userPos.getY())/7;
            double velZ = (raycastPos.getZ()-userPos.getZ())/7;

            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20, 6));
            user.addVelocity(velX,velY,velZ);
            ModEffects.Path(user,world,user.getPos(),ParticleTypes.CAMPFIRE_COSY_SMOKE,0,0,0,1,10,25);

            Objects.requireNonNull(user).getItemCooldownManager().set(this, 30);

        }

        if (user.isSneaking() && user.isOnGround()) {
            Objects.requireNonNull(user).getItemCooldownManager().set(this, 50);
            ModEffects.ShockWave(world,userPos.add(0,0.3,0),4,user);
        }

        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(Text.translatable("tooltip.ruberia.amethyst_staff"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
