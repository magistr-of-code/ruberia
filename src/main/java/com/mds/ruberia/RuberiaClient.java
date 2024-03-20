package com.mds.ruberia;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.entity.ModEntities;
import com.mds.ruberia.entity.client.PurpleLightningEntityRenderer;
import com.mds.ruberia.event.KeyInputHandler;
import com.mds.ruberia.networking.ModMessages;
import com.mds.ruberia.particle.ModParticles;
import com.mds.ruberia.particle.custom.CelticParticle;
import com.mds.ruberia.particle.custom.LightningParticle;
import com.mds.ruberia.particle.custom.PurpleLightningParticle;
import com.mds.ruberia.screen.ModScreenHandlers;
import com.mds.ruberia.screen.WritingTableScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.LightningEntityRenderer;
import net.minecraft.entity.EntityType;

public class RuberiaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        KeyInputHandler.register();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PURPLE_CRYSTAL, RenderLayer.getTranslucent());

        EntityRendererRegistry.register(ModEntities.CRYSTAL_SPEAR_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PURPLE_LIGHTNING_BOLT, PurpleLightningEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.CELTIC_PARTICLE, CelticParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LIGHTNING_PARTICLE, LightningParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.PURPLE_LIGHTNING_PARTICLE, PurpleLightningParticle.Factory::new);

        ModMessages.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.WRITING_TABLE_SCREEN_HANDLER, WritingTableScreen::new);
    }
}