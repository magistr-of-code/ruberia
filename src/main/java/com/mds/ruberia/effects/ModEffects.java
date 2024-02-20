package com.mds.ruberia.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.List;
import java.util.random.RandomGenerator;

public class ModEffects {

    public static void ShockWave(World world, PlayerEntity user, double radios) {

        Vec3d pos = user.getPos();

        List<Entity> Entities = world.getOtherEntities(user,new Box(pos.getX()-radios,pos.getY()-radios,pos.getZ()-radios,pos.getX()+radios,pos.getY()+radios,pos.getZ()+radios));

        ModEffects.CircleGoingOutwards(world,pos,ParticleTypes.END_ROD,0,0,0,radios/16,radios/12);

        for (int i = 0; i != 100; i++){
            world.addParticle(ParticleTypes.END_ROD,pos.getX(),pos.getY(),pos.getZ(), RandomGenerator.getDefault().nextDouble(-((double) radios /4),((double) radios /4)),RandomGenerator.getDefault().nextDouble(-0.25,0.25),RandomGenerator.getDefault().nextDouble(-((double) radios /4),((double) radios /4)));
        }

        for (int i = 0; i != Entities.toArray().length; i++) {

            Entity entity = Entities.get(i);

            double z,y,x;

            double aa = Math.sqrt((x = entity.getX() - pos.getX()) * x + (y = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - pos.getY()) * y + (z = entity.getZ() - pos.getZ()) * z);

            double ad = Explosion.getExposure(pos,entity);

            x /= aa;
            y /= aa;
            z /= aa;

            Vec3d vec3d2 = new Vec3d(x * ad, y * ad, z * ad);
            entity.setVelocity(entity.getVelocity().add(vec3d2));

            world.addParticle(ParticleTypes.FLASH,entity.getPos().getX(),entity.getPos().getY(),entity.getPos().getZ(),0,0,0);
        }
    }

    public static void ShockWave(World world, Vec3d pos, double radios) {

        List<Entity> Entities = world.getOtherEntities(null,new Box(pos.getX()-radios,pos.getY()-radios,pos.getZ()-radios,pos.getX()+radios,pos.getY()+radios,pos.getZ()+radios));

        ModEffects.CircleGoingOutwards(world,pos,ParticleTypes.END_ROD,0,0,0,radios/16,radios/12);

        for (int i = 0; i != 100; i++){
            world.addParticle(ParticleTypes.END_ROD,pos.getX(),pos.getY(),pos.getZ(), RandomGenerator.getDefault().nextDouble(-((double) radios /4),((double) radios /4)),RandomGenerator.getDefault().nextDouble(-0.25,0.25),RandomGenerator.getDefault().nextDouble(-((double) radios /4),((double) radios /4)));
        }

        for (int i = 0; i != Entities.toArray().length; i++) {

            Entity entity = Entities.get(i);

            double z,y,x;

            double aa = Math.sqrt((x = entity.getX() - pos.getX()) * x + (y = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - pos.getY()) * y + (z = entity.getZ() - pos.getZ()) * z);

            double ad = Explosion.getExposure(pos,entity);

            x /= aa;
            y /= aa;
            z /= aa;

            Vec3d vec3d2 = new Vec3d(x * ad, y * ad, z * ad);
            entity.setVelocity(entity.getVelocity().add(vec3d2));

            world.addParticle(ParticleTypes.FLASH,entity.getPos().getX(),entity.getPos().getY(),entity.getPos().getZ(),0,0,0);
        }
    }

    public static void ShockWave(World world, Vec3d pos, double radios,Entity exception) {

        List<Entity> Entities = world.getOtherEntities(exception,new Box(pos.getX()-radios,pos.getY()-radios,pos.getZ()-radios,pos.getX()+radios,pos.getY()+radios,pos.getZ()+radios));

        ModEffects.CircleGoingOutwards(world,pos,ParticleTypes.END_ROD,0,0,0,radios/16,radios/12);

        for (int i = 0; i != 100; i++){
            world.addParticle(ParticleTypes.END_ROD,pos.getX(),pos.getY(),pos.getZ(), RandomGenerator.getDefault().nextDouble(-((double) radios /4),((double) radios /4)),RandomGenerator.getDefault().nextDouble(-0.25,0.25),RandomGenerator.getDefault().nextDouble(-((double) radios /4),((double) radios /4)));
        }

        for (int i = 0; i != Entities.toArray().length; i++) {

            Entity entity = Entities.get(i);

            double z,y,x;

            double aa = Math.sqrt((x = entity.getX() - pos.getX()) * x + (y = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - pos.getY()) * y + (z = entity.getZ() - pos.getZ()) * z);

            double ad = Explosion.getExposure(pos,entity);

            x /= aa;
            y /= aa;
            z /= aa;

            Vec3d vec3d2 = new Vec3d(x * ad, y * ad, z * ad);
            entity.setVelocity(entity.getVelocity().add(vec3d2));

            world.addParticle(ParticleTypes.FLASH,entity.getPos().getX(),entity.getPos().getY(),entity.getPos().getZ(),0,0,0);
        }
    }

    public static void Circle(World world, Vec3d pos, ParticleEffect particle, double velocityX, double velocityY, double velocityZ){
        for(int i = 0; i != 360; i++){
            double cos = Math.cos(Math.toDegrees(i));
            double sin = Math.sin(Math.toDegrees(i));

            world.addParticle(particle,pos.getX()+cos,pos.getY(),pos.getZ()+sin,velocityX,velocityY,velocityZ);
        }
    }

    public static void Circle(World world, Vec3d pos, ParticleEffect particle, double velocityX, double velocityY, double velocityZ,double radiusMultiplier){
        for(int i = 0; i != 360; i++){
            double cos = Math.cos(Math.toDegrees(i));
            double sin = Math.sin(Math.toDegrees(i));

            world.addParticle(particle,pos.getX()+(cos*radiusMultiplier),pos.getY(),pos.getZ()+(sin*radiusMultiplier),velocityX,velocityY,velocityZ);
        }
    }

    public static void Circle(World world, Vec3d pos, ParticleEffect particle, double velocityX, double velocityY, double velocityZ,double radiusMultiplier,int degrees){
        for(int i = 0; i != degrees; i++){
            double cos = Math.cos(Math.toDegrees(i));
            double sin = Math.sin(Math.toDegrees(i));

            world.addParticle(particle,pos.getX()+(cos*radiusMultiplier),pos.getY(),pos.getZ()+(sin*radiusMultiplier),velocityX,velocityY,velocityZ);
        }
    }

    public static void CircleGoingOutwards(World world, Vec3d pos, ParticleEffect particle, double velocityX, double velocityY, double velocityZ,double radiusMultiplier,double Speed){
        for(int i = 0; i != 360; i++){
            double cos = Math.cos(Math.toDegrees(i));
            double sin = Math.sin(Math.toDegrees(i));

            world.addParticle(particle,pos.getX()+(cos*radiusMultiplier),pos.getY(),pos.getZ()+(sin*radiusMultiplier),velocityX+(cos*Speed),velocityY,velocityZ+(sin*Speed));
        }
    }

    public static void Path(PlayerEntity user, World world, Vec3d pos, ParticleEffect particle, double velocityX, double velocityY, double velocityZ,double radiusMultiplier,int rayCastDistance,double SpeedDecreased){

        Vec3d raycastPos = user.raycast(rayCastDistance,2.0f,false).getPos();

        Vec3d userPos = user.getPos();

        double backwardsVelX = (raycastPos.getX()-userPos.getX())/SpeedDecreased;
        double backwardsVelY = (raycastPos.getY()-userPos.getY())/SpeedDecreased;
        double backwardsVelZ = (raycastPos.getZ()-userPos.getZ())/SpeedDecreased;

        for(int i = 0; i != 10; i++){
            double cos = Math.cos(Math.toDegrees(i));
            double sin = Math.sin(Math.toDegrees(i));

            world.addParticle(particle,pos.getX()+(cos*radiusMultiplier),pos.getY(),pos.getZ()+(sin*radiusMultiplier),velocityX-backwardsVelX,velocityY-backwardsVelY,velocityZ-backwardsVelZ);
        }
    }
}
