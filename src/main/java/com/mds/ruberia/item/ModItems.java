package com.mds.ruberia.item;

import com.mds.ruberia.Ruberia;
import com.mds.ruberia.item.custom.AmethystStaffItem;
import com.mds.ruberia.item.custom.BarrierNecklace;
import com.mds.ruberia.item.custom.FlariumNecklace;
import com.mds.ruberia.item.custom.SpellBook;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class

ModItems {

    public static final Item FLARIUM_INGOT = registerItem("flarium_ingot",new Item(new FabricItemSettings()));
    public static final Item AMETHYST_STAFF = registerItem("amethyst_staff",new AmethystStaffItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item BARRIER_CRYSTAL = registerItem("barrier_crystal",new Item(new FabricItemSettings().food(ModFoodComponents.BARRIER_CRYSTAL)));
    public static final Item COAL_COKE = registerItem("coal_coke",new Item(new FabricItemSettings()));
    public static final Item FLARIUM_NECKLACE = registerItem("flarium_necklace",new FlariumNecklace(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item BARRIER_NECKLACE = registerItem("barrier_necklace",new BarrierNecklace(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));
    public static final Item SPELL_BOOK = registerItem("spell_book",new SpellBook(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC)));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(FLARIUM_INGOT);
    }

    private static Item registerItem(String name,Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Ruberia.MOD_ID, name), item);
    }



    public static void registerModItems() {

        FuelRegistry.INSTANCE.add(ModItems.COAL_COKE,8000);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);

        Ruberia.LOGGER.info("Registering mod items for " + Ruberia.MOD_ID);
    }
}
