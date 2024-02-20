package com.mds.ruberia.effects;

import com.mds.ruberia.Ruberia;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModStatusEffects {
    public static final StatusEffect GHOST = new GhostStatusEffect();

    public static void registerModStatusEffects () {
        Ruberia.LOGGER.info("Registering mod status effects for " + Ruberia.MOD_ID);

        Registry.register(Registries.STATUS_EFFECT, new Identifier(Ruberia.MOD_ID, "ghost"), GHOST);
    }

}
