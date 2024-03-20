package com.mds.ruberia.enchantment;

import com.mds.ruberia.Ruberia;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    public static Enchantment FIRE_STRIKE = register("lightning_strike",
            new LightingStrikeEnchantment(Enchantment.Rarity.VERY_RARE));
    public static Enchantment DASH = register("dash",
            new DashEnchantment(Enchantment.Rarity.VERY_RARE));

    public static Enchantment register(String name, Enchantment enchantment){
        return Registry.register(Registries.ENCHANTMENT, new Identifier(Ruberia.MOD_ID,name),enchantment);
    }

    public static void registerModEnchantments(){
        Ruberia.LOGGER.info("Registering Enchantments for "+Ruberia.MOD_ID);
    }
}
