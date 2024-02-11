package com.mds.ruberia.item;

import com.mds.ruberia.Ruberia;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup FLARIUM_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Ruberia.MOD_ID,"flarium"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.flarium"))
                    .icon(() -> new ItemStack(ModItems.FLARIUM_INGOT)).entries((displayContext, entries) -> {
                        entries.add(ModItems.FLARIUM_INGOT);
                    }).build());

    public static void registerItemGroups(){
        Ruberia.LOGGER.info("Registering item groups for " + Ruberia.MOD_ID);
    }
}
