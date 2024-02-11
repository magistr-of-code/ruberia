package com.mds.ruberia.item;

import com.mds.ruberia.Ruberia;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item FLARIUM_INGOT = registerItem("flarium_ingot",new Item(new FabricItemSettings()));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(FLARIUM_INGOT);
    }

    private static Item registerItem(String name,Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Ruberia.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Ruberia.LOGGER.info("Registering mod items for " + Ruberia.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
