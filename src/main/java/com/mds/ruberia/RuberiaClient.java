package com.mds.ruberia;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.entity.ModEntities;
import com.mds.ruberia.event.KeyInputHandler;
import com.mds.ruberia.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class RuberiaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_CRYSTAL, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(ModEntities.CRYSTAL_SPEAR_ENTITY, FlyingItemEntityRenderer::new);

        ModMessages.registerS2CPackets();

    }
}