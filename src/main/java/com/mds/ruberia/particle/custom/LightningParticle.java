package com.mds.ruberia.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class LightningParticle extends SpriteBillboardParticle {

    private final SpriteProvider sprites;

    public LightningParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
                             SpriteProvider spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, 0, 0, 0);
        this.velocityMultiplier = 0.0F;
        this.scale *= 4.0F;
        this.maxAge = 20;
        this.sprites = spriteSet;
        this.setSpriteForAge(spriteSet);
        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
        this.x=xCoord;
        this.y=yCoord;
        this.z=zCoord;
        this.setPos(xCoord,yCoord,zCoord);
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
        randomlyAnimate();
        float xj = (this.random.nextFloat() / 12.0F * (float) (this.random.nextBoolean() ? 1 : -1));
        float yj = (this.random.nextFloat() / 12.0F * (float) (this.random.nextBoolean() ? 1 : -1));
        float zj = (this.random.nextFloat() / 12.0F * (float) (this.random.nextBoolean() ? 1 : -1));
        setPos(x + xj, y + yj, z + zj);
        //damage(1f);
    }

    public Vec3d getPos(){
        return new Vec3d(x,y,z);
    }

    public void damage(float amount) {
        Vec3d pos = getPos();
        int power = 2;
        List<Entity> Entities = world.getOtherEntities(null,new Box(pos.getX()-power,pos.getY()-power,pos.getZ()-power,pos.getX()+power,pos.getY()+power,pos.getZ()+power));

        for (int i = 0; i != Entities.size();i++){
            Entity entity = Entities.get(i);
            if (entity.isLiving()){
                entity.damage(entity.getDamageSources().lightningBolt(),amount);
            }
        }
    }

    private void randomlyAnimate() {
        setSprite(sprites.getSprite(Random.create()));
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)maxAge) * age + 1);
    }


    @Override
    protected int getBrightness(float tint) {
        return LightmapTextureManager.MAX_LIGHT_COORDINATE;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new LightningParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
