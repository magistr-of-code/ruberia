package com.mds.ruberia;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.effects.ModStatusEffects;
import com.mds.ruberia.item.ModItemGroups;
import com.mds.ruberia.item.ModItems;
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

		LOGGER.info("Hello Fabric world!");
	}
}