package com.mds.ruberia.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.List;
import java.util.random.RandomGenerator;

public class ModEffects {

    public static void Explosion(World world, double power,Vec3d pos,List<Entity> exceptions) {

        List<Entity> Entities = world.getOtherEntities(null,new Box(pos.getX()-power,pos.getY()-power,pos.getZ()-power,pos.getX()+power,pos.getY()+power,pos.getZ()+power));
        if (exceptions!=null) {
            for (int i = 0; i != exceptions.size();i++) {
                Entities.remove(exceptions.get(i));
            }
        }

        for (int i = 0; i != Entities.size(); i++) {

            Entity entity = Entities.get(i);

            double z,y,x;

            double aa = Math.sqrt((x = entity.getX() - pos.getX()) * x + (y = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - pos.getY()) * y + (z = entity.getZ() - pos.getZ()) * z);

            double ad = Explosion.getExposure(pos,entity);

            x /= aa;
            y /= aa;
            z /= aa;

            Vec3d vec3d2 = new Vec3d((x * ad)*power/12, (y * ad)*power/12, (z * ad)*power/12);
            entity.setVelocity(entity.getVelocity().add(vec3d2));
        }

    }

    public static void ShockWave(World world, Vec3d pos, double radios,List<Entity> exceptions) {

        for (int i = 0; i != 100; i++){
            world.addParticle(ParticleTypes.END_ROD,pos.getX(),pos.getY(),pos.getZ(), RandomGenerator.getDefault().nextDouble(-(radios /4),(radios /4)),RandomGenerator.getDefault().nextDouble(-0.25,0.25),RandomGenerator.getDefault().nextDouble(-(radios /4),(radios /4)));
        }

        ModEffects.CircleGoingOutwards(world,pos,ParticleTypes.END_ROD,0,0,0,radios/16,radios/12);

        ModEffects.Explosion(world,radios,pos,exceptions);

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

    public static void Path(LivingEntity user, World world, Vec3d pos, ParticleEffect particle, double radiusMultiplier, int rayCastDistance, double SpeedDecreased){

        Vec3d raycastPos = user.raycast(rayCastDistance,2.0f,false).getPos();

        double backwardsVelX = (raycastPos.getX()-pos.getX())/SpeedDecreased;
        double backwardsVelY = (raycastPos.getY()-pos.getY())/SpeedDecreased;
        double backwardsVelZ = (raycastPos.getZ()-pos.getZ())/SpeedDecreased;

        ModEffects.Explosion(world,rayCastDistance,pos,List.of(user));

        ModEffects.Circle(world,pos,particle,0-backwardsVelX,0-backwardsVelY,0-backwardsVelZ,radiusMultiplier,10);
    }

    public static Vec3d LookPos(Entity entity,double x,double y, double z){
        Vec2f vec2f = entity.getRotationClient();
        Vec3d vec3d = entity.getPos();
        float f = MathHelper.cos((vec2f.y + 90.0F) * 0.017453292F);
        float g = MathHelper.sin((vec2f.y + 90.0F) * 0.017453292F);
        float h = MathHelper.cos(-vec2f.x * 0.017453292F);
        float i = MathHelper.sin(-vec2f.x * 0.017453292F);
        float j = MathHelper.cos((-vec2f.x + 90.0F) * 0.017453292F);
        float k = MathHelper.sin((-vec2f.x + 90.0F) * 0.017453292F);
        Vec3d vec3d2 = new Vec3d((double)(f * h), (double)i, (double)(g * h));
        Vec3d vec3d3 = new Vec3d((double)(f * j), (double)k, (double)(g * j));
        Vec3d vec3d4 = vec3d2.crossProduct(vec3d3).multiply(-1.0);
        double d = vec3d2.x * z + vec3d3.x * y + vec3d4.x * x;
        double e = vec3d2.y * z + vec3d3.y * y + vec3d4.y * x;
        double l = vec3d2.z * z + vec3d3.z * y + vec3d4.z * x;
        return new Vec3d(vec3d.x + d, vec3d.y + e, vec3d.z + l);
    }

    public static Vec3d LookPosAs(Entity entity,Entity entity2,double x,double y, double z){
        Vec2f vec2f = entity2.getRotationClient();
        Vec3d vec3d = entity.getPos();
        float f = MathHelper.cos((vec2f.y + 90.0F) * 0.017453292F);
        float g = MathHelper.sin((vec2f.y + 90.0F) * 0.017453292F);
        float h = MathHelper.cos(-vec2f.x * 0.017453292F);
        float i = MathHelper.sin(-vec2f.x * 0.017453292F);
        float j = MathHelper.cos((-vec2f.x + 90.0F) * 0.017453292F);
        float k = MathHelper.sin((-vec2f.x + 90.0F) * 0.017453292F);
        Vec3d vec3d2 = new Vec3d((double)(f * h), (double)i, (double)(g * h));
        Vec3d vec3d3 = new Vec3d((double)(f * j), (double)k, (double)(g * j));
        Vec3d vec3d4 = vec3d2.crossProduct(vec3d3).multiply(-1.0);
        double d = vec3d2.x * z + vec3d3.x * y + vec3d4.x * x;
        double e = vec3d2.y * z + vec3d3.y * y + vec3d4.y * x;
        double l = vec3d2.z * z + vec3d3.z * y + vec3d4.z * x;
        return new Vec3d(vec3d.x + d, vec3d.y + e, vec3d.z + l);
    }
}
