package com.mds.ruberia.item.custom;

import com.mds.ruberia.effects.ModEffects;
import com.mds.ruberia.entity.CrystalSpear;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AmethystStaffItem extends Item implements Vanishable {

    public AmethystStaffItem(Settings settings) {
        super(settings);
    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0f;
        if ((f = (f * f + f * 2.0f) / 3.0f) > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.TOOT_HORN;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

        int i;
        float f;

        if (!(user instanceof PlayerEntity playerEntity)) {
            return;
        }

        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

        if ((double)(f = AmethystStaffItem.getPullProgress(i = this.getMaxUseTime(stack) - remainingUseTicks)) < 0.1) {
            return;
        }

        Vec3d playerEntityPos = playerEntity.getPos();

        if (!playerEntity.isSneaking()) {
            if (!world.isClient) {
                CrystalSpear crystalSpearEntity = new CrystalSpear(world, playerEntity);
                crystalSpearEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 4F, 0F);
                //crystalSpearEntity.setNoGravity(true);

                world.spawnEntity(crystalSpearEntity);
            }
        }

        if(playerEntity.isSneaking() && !playerEntity.isOnGround()) {

            Vec3d raycastPos = playerEntity.raycast(10,2.0f,false).getPos();

            double velX = (raycastPos.getX()-playerEntityPos.getX())/7;
            double velY = (raycastPos.getY()-playerEntityPos.getY())/7;
            double velZ = (raycastPos.getZ()-playerEntityPos.getZ())/7;

            playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20, 6));
            playerEntity.addVelocity(velX,velY,velZ);
            ModEffects.Path(playerEntity,world,playerEntity.getPos(), ParticleTypes.CAMPFIRE_COSY_SMOKE,1,10,1);
            playerEntity.getItemCooldownManager().set(this, 200);

        }

        if (playerEntity.isSneaking() && playerEntity.isOnGround()) {
            playerEntity.getItemCooldownManager().set(this, 500);
            ModEffects.ShockWave(world,playerEntityPos.add(0,0.3,0),20,List.of(playerEntity));
        }

        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

       // user.tiltScreen(RandomGenerator.getDefault().nextDouble(-3,3),RandomGenerator.getDefault().nextDouble(-3,3));

        //Entity camera = Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity());

        //camera.detach();

        //camera.setPosition(user.getPos().addRandom(world.getRandom(),2));




        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.success(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(Text.translatable("tooltip.ruberia.amethyst_staff"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}