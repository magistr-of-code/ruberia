package com.mds.ruberia.networking.packet;

import com.mds.ruberia.effects.ModEffects;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;

import java.util.List;

public class ArtifactsS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        ClientPlayerEntity player = client.player;

        if (player != null) {
            if (buf.readString().equals("barrier_necklace")){
                ModEffects.ShockWave(player.getWorld(),player.getPos(),8, List.of(player));
            }
        }
    }
}
