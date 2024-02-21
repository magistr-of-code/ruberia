package com.mds.ruberia;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.entity.ModEntities;
import com.mds.ruberia.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class RuberiaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        KeyBinding activate_necklace_keybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ruberia.activate_necklace",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.ruberia.abilities"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (activate_necklace_keybinding.wasPressed()) {
                ClientPlayNetworking.send(ModMessages.ARTIFACT_ID, PacketByteBufs.create());
            }
        });

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_CRYSTAL, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(ModEntities.CRYSTAL_SPEAR_ENTITY, FlyingItemEntityRenderer::new);

        ModMessages.registerS2CPackets();

    }
}
