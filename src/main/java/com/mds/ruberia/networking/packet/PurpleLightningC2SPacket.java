package com.mds.ruberia.networking.packet;

import com.mds.ruberia.entity.ModEntities;
import com.mds.ruberia.entity.custom.PurpleLightningEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.random.RandomGenerator;

public class PurpleLightningC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        World world = player.getWorld();
        Vec3d pos = new Vec3d(player.getX()+RandomGenerator.getDefault().nextDouble(-12,12),player.getY()+RandomGenerator.getDefault().nextDouble(-12,12),player.getZ()+RandomGenerator.getDefault().nextDouble(-12,12));
        Entity entity = new PurpleLightningEntity(ModEntities.PURPLE_LIGHTNING_BOLT,world);
        entity.setPos(pos.getX(),pos.getY(),pos.getZ());
        world.spawnEntity(entity);
    }
}  
