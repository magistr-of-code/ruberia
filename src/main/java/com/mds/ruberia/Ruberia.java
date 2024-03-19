package com.mds.ruberia;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.block.entity.ModBlockEntities;
import com.mds.ruberia.effects.ModStatusEffects;
import com.mds.ruberia.enchantment.ModEnchantments;
import com.mds.ruberia.item.ModItemGroups;
import com.mds.ruberia.item.ModItems;
import com.mds.ruberia.networking.ModMessages;
import com.mds.ruberia.particle.ModParticles;
import com.mds.ruberia.recipe.ModRecipes;
import com.mds.ruberia.screen.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ruberia implements ModInitializer {

	public static final String MOD_ID = "ruberia";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModStatusEffects.registerModStatusEffects();

		ModMessages.registerC2SPackets();

		ModParticles.registerParticles();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModRecipes.registerRecipes();

		ModEnchantments.registerModEnchantments();

		LOGGER.info("Hello Fabric world!");
	}
}