package com.mds.ruberia.item.custom.spell_casting_items;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.effects.ModEffects;
import com.mds.ruberia.entity.ModEntities;
import com.mds.ruberia.entity.custom.CrystalSpear;
import com.mds.ruberia.entity.custom.PurpleLightningEntity;
import com.mds.ruberia.particle.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.random.RandomGenerator;

public class SpellCastingItem extends Item {

    public double getManaMultiplier() {
        return 10;
    }

    public SpellCastingItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getOrCreateNbt().getInt("active_spells")>=1;
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
        return UseAction.BOW;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

        if (!(user instanceof PlayerEntity playerEntity)) {
            return;
        }

        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

        if ((double)(SpellCastingItem.getPullProgress(this.getMaxUseTime(stack) - remainingUseTicks)) < 0.1) {
            return;
        }

        NbtCompound nbtCompound = stack.getNbt();

        if (nbtCompound != null) {
            for (int currentSpell = 1; currentSpell != nbtCompound.getInt("active_spells")+1; currentSpell++){

                String activeSpell = getActiveSpell(nbtCompound,currentSpell);

                int s = 1;

                for (int f = currentSpell+1; getActiveSpell(nbtCompound,f).equals("multiplier");f++){
                    s = f-currentSpell;
                }

                if (activeSpell.equals("crystal_spear")){
                    crystalSpearSpell(playerEntity,world,s);
                }
                if (activeSpell.equals("shield")){
                    shieldSpell(playerEntity,world,s);
                }
                if (activeSpell.equals("crystal")) {
                    crystalSpell(playerEntity,world,s);
                }
                if (activeSpell.equals("shockwave")) {
                    shockwaveSpell(playerEntity,world,s);
                }
                if (activeSpell.equals("teleport")) {
                    teleportSpell(playerEntity,world,s);
                }
                if (activeSpell.equals("power_word_death")){
                    powerWordDeathSpell(playerEntity,world);
                }
                if (activeSpell.equals("urdzam")){
                    urdzamSpell(playerEntity,world);
                }
            }
            world.addParticle(ModParticles.CELTIC_PARTICLE,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(),0,0,0);
            playerEntity.getItemCooldownManager().set(this, (int) (nbtCompound.getInt("mana")*getManaMultiplier()));
            stack.setNbt(null);
        }

        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public static void urdzamSpell(PlayerEntity playerEntity, World world) {
        for (int i = 0; i!=450;i++){
            Vec3d pos = new Vec3d(playerEntity.getX()+ RandomGenerator.getDefault().nextDouble(-12,12),playerEntity.getY()+RandomGenerator.getDefault().nextDouble(-12,12),playerEntity.getZ()+RandomGenerator.getDefault().nextDouble(-12,12));
            world.addParticle(ModParticles.PURPLE_LIGHTNING_PARTICLE,pos.getX(),pos.getY(),pos.getZ(),0,0,0);
            Entity entity = new PurpleLightningEntity(ModEntities.PURPLE_LIGHTNING_BOLT,world);
            for (double z = pos.getY(); world.getBlockState(new BlockPos((int) pos.getX(), (int) (z-1), (int) pos.getZ()))== Blocks.AIR.getDefaultState(); z--){
                pos.subtract(0,1,0);
            }
            entity.setPos(pos.getX(),pos.getY(),pos.getZ());
            world.spawnEntity(entity);
        }
        Vec3d pos = playerEntity.getPos();
        int power = 12;
        List<Entity> Entities = world.getOtherEntities(playerEntity,new Box(pos.getX()-power,pos.getY()-power,pos.getZ()-power,pos.getX()+power,pos.getY()+power,pos.getZ()+power));

        for (int i = 0; i != Entities.size();i++){
            Entity entity = Entities.get(i);
            if (entity.isLiving()&&entity.isInRange(entity,power)){
                entity.damage(entity.getDamageSources().magic(),5f);
            }
        }
    }

    public static void powerWordDeathSpell(PlayerEntity playerEntity, World world) {

        if (RandomGenerator.getDefault().nextBoolean()){
            Vec3d pos = playerEntity.raycast(10,1,true).getPos();
            int power = 4;

            List<Entity> Entities = world.getOtherEntities(playerEntity,new Box(pos.getX()-power,pos.getY()-power,pos.getZ()-power,pos.getX()+power,pos.getY()+power,pos.getZ()+power));

            for (int i = 0; i != Entities.size();i++){
                Entity entity = Entities.get(i);
                if (entity.isLiving()&&entity.isInRange(entity,power)){
                    world.addParticle(ModParticles.CELTIC_PARTICLE,entity.getX(),entity.getY(),entity.getZ(),0,0,0);
                    for (int y = 0;y!=100;y++){
                        world.addParticle(ParticleTypes.FLAME,entity.getX(),entity.getY()+1+RandomGenerator.getDefault().nextDouble(-1,1),entity.getZ(),RandomGenerator.getDefault().nextDouble(-0.2,0.2),RandomGenerator.getDefault().nextDouble(-0.2,0.2),RandomGenerator.getDefault().nextDouble(-0.2,0.2));
                    }
                    entity.kill();
                }
            }
        } else {
            world.addParticle(ModParticles.CELTIC_PARTICLE,playerEntity.getX(),playerEntity.getY(),playerEntity.getZ(),0,0,0);
            for (int y = 0;y!=100;y++){
                world.addParticle(ParticleTypes.FLAME,playerEntity.getX(),playerEntity.getY()+1+RandomGenerator.getDefault().nextDouble(-1,1),playerEntity.getZ(),RandomGenerator.getDefault().nextDouble(-0.2,0.2),RandomGenerator.getDefault().nextDouble(-0.2,0.2),RandomGenerator.getDefault().nextDouble(-0.2,0.2));
            }
            playerEntity.kill();
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {


        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.success(itemStack);
    }

    public static String getActiveSpell(NbtCompound nbtCompound,int index){
        return nbtCompound.getString("active_spell_" + index);
    }

    public static void crystalSpearSpell(PlayerEntity player,World world,int s){
        CrystalSpear crystalSpearEntity = new CrystalSpear(world, player);
        crystalSpearEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 4F, 0F);

        crystalSpearEntity.setS(s);

        world.spawnEntity(crystalSpearEntity);
    }

    public static void shockwaveSpell(PlayerEntity player,World world,int s){
        ModEffects.ShockWave(world,player.getPos(),s+9, List.of(player));
    }

    public static void shieldSpell(PlayerEntity player,World world,int s){
        if (!world.isClient){
            for (int i = 0;i <= 2+s;i++) {
                BlockPos blockPos = BlockPos.ofFloored(ModEffects.LookPos(player,i,-i,2));
                if (blockIsAvailable(blockPos,world)){
                    if (world.getBlockState(blockPos)!=Blocks.AIR.getDefaultState()){
                        world.breakBlock(blockPos,true);
                    }
                    world.setBlockState(blockPos, ModBlocks.FLARIUM_BARRIER.getDefaultState());
                    for (int z = 0; z <= 20; z++){
                        world.addParticle(ParticleTypes.END_ROD,blockPos.getX(),blockPos.getY(),blockPos.getZ(), RandomGenerator.getDefault().nextDouble(-0.5,0.5), RandomGenerator.getDefault().nextDouble(-0.5,0.5),RandomGenerator.getDefault().nextDouble(-0.5,0.5));
                    }
                    world.playSound(null,blockPos, SoundEvents.BLOCK_METAL_FALL, SoundCategory.AMBIENT,1,2);
                }
            }
        }
        for (int i = 0;i <= 2+s;i++) {
            BlockPos blockPos = BlockPos.ofFloored(ModEffects.LookPos(player,-i,i,2));
            if (blockIsAvailable(blockPos,world)){
                if (world.getBlockState(blockPos)!=Blocks.AIR.getDefaultState()){
                    world.breakBlock(blockPos,true);
                }
                for (int z = 0; z <= 20; z++){
                    world.addParticle(ParticleTypes.END_ROD,blockPos.getX(),blockPos.getY(),blockPos.getZ(), RandomGenerator.getDefault().nextDouble(-0.5,0.5), RandomGenerator.getDefault().nextDouble(-0.5,0.5),RandomGenerator.getDefault().nextDouble(-0.5,0.5));
                }
                world.setBlockState(blockPos, ModBlocks.FLARIUM_BARRIER.getDefaultState());
            }
        }
        for (int i = 0;i <= 2+s;i++) {
            BlockPos blockPos = BlockPos.ofFloored(ModEffects.LookPos(player,i,i,2));
            if (blockIsAvailable(blockPos,world)){
                if (world.getBlockState(blockPos)!=Blocks.AIR.getDefaultState()){
                    world.breakBlock(blockPos,true);
                }
                for (int z = 0; z <= 20; z++){
                    world.addParticle(ParticleTypes.END_ROD,blockPos.getX(),blockPos.getY(),blockPos.getZ(), RandomGenerator.getDefault().nextDouble(-0.5,0.5), RandomGenerator.getDefault().nextDouble(-0.5,0.5),RandomGenerator.getDefault().nextDouble(-0.5,0.5));
                }
                world.setBlockState(blockPos, ModBlocks.FLARIUM_BARRIER.getDefaultState());
            }
        }
        for (int i = 0;i <= 2+s;i++) {
            BlockPos blockPos = BlockPos.ofFloored(ModEffects.LookPos(player,-i,-i,2));
            if (blockIsAvailable(blockPos,world)){
                if (world.getBlockState(blockPos)!=Blocks.AIR.getDefaultState()){
                    world.breakBlock(blockPos,true);
                }
                for (int z = 0; z <= 20; z++){
                    world.addParticle(ParticleTypes.END_ROD,blockPos.getX(),blockPos.getY(),blockPos.getZ(), RandomGenerator.getDefault().nextDouble(-0.5,0.5), RandomGenerator.getDefault().nextDouble(-0.5,0.5),RandomGenerator.getDefault().nextDouble(-0.5,0.5));
                }
                world.playSound(null,blockPos, SoundEvents.BLOCK_METAL_FALL, SoundCategory.AMBIENT,1,2);
                world.setBlockState(blockPos, ModBlocks.FLARIUM_BARRIER.getDefaultState());
            }
        }
    }

    public static void teleportSpell(PlayerEntity player,World world,int s){

        Vec3d vec3d = player.raycast(10*s,1,true).getPos();

        world.playSound(null,player.getBlockPos(),SoundEvents.ENTITY_ENDERMAN_TELEPORT,SoundCategory.PLAYERS,1,1);
        world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(player));

        player.setPos(vec3d.getX(),vec3d.getY(),vec3d.getZ());
    }

    public static void crystalSpell(PlayerEntity player,World world,int s){


    }

    public static boolean blockIsAvailable(BlockPos pos,World world){
        BlockState state = world.getBlockState(pos);
        return world.canSetBlock(pos) && !state.equals(Blocks.BEDROCK.getDefaultState())&&!state.equals(Blocks.OBSIDIAN.getDefaultState())&&state.getHardness(world,pos)<=Blocks.OBSIDIAN.getHardness();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        NbtCompound nbtCompound = stack.getNbt();

        if (nbtCompound != null) {
            for (int currentSpell = 1; currentSpell != nbtCompound.getInt("active_spells") + 1; currentSpell++) {
                String spell = "spell.ruberia."+getActiveSpell(nbtCompound,currentSpell);
                Text text = Text.translatable(spell);
                tooltip.add(text);
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
