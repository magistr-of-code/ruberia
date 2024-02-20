package com.mds.ruberia.item;

import com.mds.ruberia.effects.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent BARRIER_CRYSTAL = new FoodComponent.Builder().hunger(3).saturationModifier(1.5f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(ModStatusEffects.GHOST,300,2),1f).build();
}
