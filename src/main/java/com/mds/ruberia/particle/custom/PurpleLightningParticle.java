package com.mds.ruberia.particle.custom;

import com.mds.ruberia.entity.ModEntities;
import com.mds.ruberia.entity.custom.PurpleLightningEntity;
import com.mds.ruberia.networking.ModMessages;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;

import java.util.random.RandomGenerator;

public class PurpleLightningParticle extends LightningParticle {

    public PurpleLightningParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
                                   SpriteProvider spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, spriteSet ,0, 0, 0);
        this.maxAge = 90;
        this.red = (float) 138 / 255;
        this.green = (float) 43 / 255;
        this.blue = (float) 226 / 255;
    }

    @Override
    public void tick() {
        super.tick();
        int in = random.nextInt(350);
        if (in==1){
            System.out.println("lightning!");
            Vec3d pos = new Vec3d(getPos().getX()+RandomGenerator.getDefault().nextDouble(-12,12),getPos().getY()+RandomGenerator.getDefault().nextDouble(-12,12),getPos().getZ()+RandomGenerator.getDefault().nextDouble(-12,12));
            Entity entity = new PurpleLightningEntity(ModEntities.PURPLE_LIGHTNING_BOLT,world);
            entity.setPos(pos.getX(),pos.getY(),pos.getZ());
            world.spawnEntity(entity);
            PacketByteBuf buf = PacketByteBufs.create();
            ClientPlayNetworking.send(ModMessages.PURPLE_LIGHTNING_ID, buf);
        }

    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new PurpleLightningParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
