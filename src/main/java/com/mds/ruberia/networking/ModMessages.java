package com.mds.ruberia.networking;

import com.mds.ruberia.Ruberia;
import com.mds.ruberia.networking.packet.ArtifactsC2SPacket;
import com.mds.ruberia.networking.packet.ArtifactsS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier ARTIFACT_ID = new Identifier(Ruberia.MOD_ID,"artifact");
    public static final Identifier ARTIFACT_CLIENT_ID = new Identifier(Ruberia.MOD_ID,"artifact_client");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(ARTIFACT_ID, ArtifactsC2SPacket::receive);
    }

    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(ARTIFACT_CLIENT_ID, ArtifactsS2CPacket::receive);
    }

}
