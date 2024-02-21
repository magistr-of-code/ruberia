package com.mds.ruberia.networking;

import com.mds.ruberia.Ruberia;
import com.mds.ruberia.networking.packet.ArtifactsC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessages {

    public static final Identifier ARTIFACT_ID = new Identifier(Ruberia.MOD_ID,"necklace");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(ARTIFACT_ID, ArtifactsC2SPacket::receive);
    }

    public static void registerS2CPackets(){

    }

}
