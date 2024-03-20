package com.mds.ruberia.event;

import com.mds.ruberia.effects.ModEffects;
import com.mds.ruberia.enchantment.ModEnchantments;
import com.mds.ruberia.item.ModItems;
import com.mds.ruberia.networking.ModMessages;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Optional;

public class KeyInputHandler {

    public static KeyBinding activate_necklace_keybinding;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (activate_necklace_keybinding.wasPressed()) {
                ClientPlayNetworking.send(ModMessages.ARTIFACT_ID, PacketByteBufs.create());
                ClientPlayerEntity player = client.player;
                if (player != null) {

                    Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(player);
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
                        player.getItemCooldownManager().set(player.getInventory().getArmorStack(0).getItem(),120*level);
                        Vec3d raycastPos = player.raycast(10,2.0f,false).getPos();

                        double velX = (raycastPos.getX()-player.getX())/21*level;
                        double velY = (raycastPos.getY()-player.getY())/21*level;
                        double velZ = (raycastPos.getZ()-player.getZ())/21*level;

                        player.addVelocity(velX,velY,velZ);
                        ModEffects.Path(player,player.getWorld(),player.getPos(), ParticleTypes.CAMPFIRE_COSY_SMOKE,1,10,1);
                    }
                }
            }
        });
    }

    public static void register(){
         activate_necklace_keybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ruberia.activate_artifacts",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.ruberia.abilities"
        ));

         registerKeyInputs();
    }
}
