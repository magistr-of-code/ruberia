package com.mds.ruberia.networking.packet;

import com.mds.ruberia.effects.ModEffects;
import com.mds.ruberia.enchantment.ModEnchantments;
import com.mds.ruberia.item.ModItems;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Optional;

public class ArtifactsC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){

        Optional< TrinketComponent > trinketComponent = TrinketsApi.getTrinketComponent(player);
        for(int i = 0; i != trinketComponent.get().getAllEquipped().size();i++) {

            Item item = trinketComponent.get().getAllEquipped().get(i).getRight().getItem();

            if(item == ModItems.BARRIER_NECKLACE && player.getInventory().contains(ModItems.BARRIER_CRYSTAL.getDefaultStack())){
                ItemStack stack = player.getInventory().getStack(player.getInventory().indexOf(ModItems.BARRIER_CRYSTAL.getDefaultStack()));
                stack.decrement(1);
                ModEffects.ShockWave(player.getWorld(),player.getPos(),4, List.of(player));
            }
        }

        int level = EnchantmentHelper.getEquipmentLevel(ModEnchantments.DASH, player);
        if (level >= 1&&!player.getItemCooldownManager().isCoolingDown(player.getInventory().getArmorStack(0).getItem())){
            Vec3d raycastPos = player.raycast(10,2.0f,false).getPos();

            double velX = (raycastPos.getX()-player.getX())/21*level;
            double velY = (raycastPos.getY()-player.getY())/21*level;
            double velZ = (raycastPos.getZ()-player.getZ())/21*level;

            player.addVelocity(velX,velY,velZ);
            player.getItemCooldownManager().set(player.getInventory().getArmorStack(0).getItem(),120*level);
            ModEffects.Path(player,player.getWorld(),player.getPos(), ParticleTypes.CAMPFIRE_COSY_SMOKE,1,10,1);
        }
    }
}  
