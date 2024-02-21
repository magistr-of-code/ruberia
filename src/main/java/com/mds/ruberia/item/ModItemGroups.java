package com.mds.ruberia.item;

import com.mds.ruberia.Ruberia;
import com.mds.ruberia.block.ModBlocks;
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
                        entries.add(ModBlocks.FLARIUM_BLOCK);
                        entries.add(ModItems.FLARIUM_NECKLACE);
                    }).build());

    public static final ItemGroup MAGIC_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Ruberia.MOD_ID,"magic"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.magic"))
                    .icon(() -> new ItemStack(ModBlocks.RUNE_BLOCK)).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.RUNE_BLOCK);
                        entries.add(ModBlocks.PURPLE_CRYSTAL);
                        entries.add(ModItems.AMETHYST_STAFF);
                        entries.add(ModBlocks.BARRIER_GENERATOR);
                        entries.add(ModItems.BARRIER_CRYSTAL);
                        entries.add(ModItems.BARRIER_NECKLACE);
                    }).build());

    public static void registerItemGroups(){
        Ruberia.LOGGER.info("Registering item groups for " + Ruberia.MOD_ID);
    }
}
