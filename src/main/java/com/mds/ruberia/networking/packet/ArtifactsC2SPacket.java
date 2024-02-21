package com.mds.ruberia.networking.packet;

import com.mds.ruberia.effects.ModEffects;
import com.mds.ruberia.item.ModItems;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.Optional;

public class ArtifactsC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){

        Optional< TrinketComponent > trinketComponent = TrinketsApi.getTrinketComponent(player);
        for(int i = 0; i != trinketComponent.get().getAllEquipped().size();i++) {
            if( trinketComponent.get().getAllEquipped().get(i).getRight().getItem() == ModItems.BARRIER_NECKLACE){
                System.out.println("success");
                ModEffects.ShockWave(player.getWorld(),player.getPos(),8, List.of(player));
            }
        }
    }
}
