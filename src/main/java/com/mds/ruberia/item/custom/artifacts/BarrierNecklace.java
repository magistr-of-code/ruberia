package com.mds.ruberia.item.custom.artifacts;

import com.google.common.collect.Multimap;
import com.mds.ruberia.effects.ModEffects;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.UUID;

public class BarrierNecklace extends TrinketItem {

    public BarrierNecklace(Settings settings) {
        super(settings);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);

        return modifiers;
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {

        entity.damage(entity.getDamageSources().magic(),2f);

        super.onUnequip(stack, slot, entity);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {

        ModEffects.ShockWave(entity.getWorld(),entity.getPos(),6, List.of(entity));

        super.onEquip(stack, slot, entity);
    }
}