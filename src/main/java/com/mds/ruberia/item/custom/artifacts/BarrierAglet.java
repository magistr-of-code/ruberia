package com.mds.ruberia.item.custom.artifacts;

import com.google.common.collect.Multimap;
import com.mds.ruberia.effects.ModEffects;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.UUID;

public class BarrierAglet extends TrinketItem {

    public BarrierAglet(Settings settings) {
        super(settings);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);

        return modifiers;
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {

        if (entity.isPlayer()&&slot.index()==0){
            PlayerEntity player = (PlayerEntity) entity;
            if (player.isTouchingWater() || player.getWorld().getBlockState(player.getBlockPos().down())==Blocks.WATER.getDefaultState()){
                player.setNoGravity(true);
            } else if (player.hasNoGravity()){
                player.setNoGravity(false);
            }
        }

        super.tick(stack, slot, entity);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {

        if (entity.isPlayer()){
            PlayerEntity player = (PlayerEntity) entity;
            if (player.hasNoGravity()){
                player.setNoGravity(false);
            }
        }

        entity.damage(entity.getDamageSources().magic(),1f);

        super.onUnequip(stack, slot, entity);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {

        ModEffects.ShockWave(entity.getWorld(),entity.getPos(),3, List.of(entity));

        super.onEquip(stack, slot, entity);
    }
}