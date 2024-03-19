package com.mds.ruberia.particle;

import com.mds.ruberia.Ruberia;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType CELTIC_PARTICLE = register("celtic_particle");

    private static DefaultParticleType register(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, new Identifier(Ruberia.MOD_ID,name), FabricParticleTypes.simple());
    }

    public static void registerParticles() {
        Ruberia.LOGGER.info("Registering Mod Particles for "+ Ruberia.MOD_ID);
    }
}
