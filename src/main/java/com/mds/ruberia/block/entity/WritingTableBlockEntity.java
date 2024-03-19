package com.mds.ruberia.block.entity;

import com.mds.ruberia.block.ModBlocks;
import com.mds.ruberia.item.ModItems;
import com.mds.ruberia.screen.WritingTableScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WritingTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory,   ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3,ItemStack.EMPTY);

    public static final int INPUT_OUTPUT_SLOT = 1;
    public static final int INK_SLOT = 2;
    public static final int MATERIAL_SLOT = 0;

    List<Item> possibleMaterials = List.of(
            ModItems.BARRIER_CRYSTAL,
            ModItems.FLARIUM_INGOT,
            Items.AMETHYST_SHARD,
            Items.ENDER_PEARL,
            Items.DIAMOND,
            ModBlocks.BARRIER_GENERATOR.asItem());

    protected final PropertyDelegate propertyDelegate;
    private int max_mana = 240;


    public WritingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WRITING_TABLE_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> WritingTableBlockEntity.this.max_mana;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> WritingTableBlockEntity.this.max_mana = value;
                }
            }

            @Override
            public int size() {
                return 1;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,inventory);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.ruberia.writing_table");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WritingTableScreenHandler(syncId,playerInventory,this,this.propertyDelegate);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient){
            return;
        }

        if (this.hasRecipe()){
            markDirty(world,pos,state);
            this.applySpell();
        }
    }

    private void applySpell() {
        ItemStack inputStack = getStack(INPUT_OUTPUT_SLOT);
        NbtCompound nbtData = inputStack.getOrCreateNbt();
        Item material = getStack(MATERIAL_SLOT).getItem();
        String spell = "no spell";

        int active_spells = nbtData.getInt("active_spells");
        int mana = nbtData.getInt("mana");

        if (material == possibleMaterials.get(0)){
            spell = "crystal_spear";
            nbtData.putInt("mana",mana+20);
        }
        if (material == possibleMaterials.get(1)){
            spell = "shield";
            nbtData.putInt("mana",mana+40);
        }
        if (material == possibleMaterials.get(2)){
            spell = "crystal";
            nbtData.putInt("mana",mana+40);
        }
        if (material == possibleMaterials.get(3)){
            spell = "teleport";
            nbtData.putInt("mana",mana+20);
        }
        if (material == possibleMaterials.get(4) && active_spells>0) {
            spell = "multiplier";
            nbtData.putInt("mana",mana+20);
        }
        if (material == possibleMaterials.get(5)) {
            spell = "shockwave";
            nbtData.putInt("mana",mana+30);
        }

        System.out.println(spell + " is active");

        nbtData.putString("active_spell_"+active_spells,spell);
        nbtData.putInt("active_spells",active_spells+1);

        this.setStack(INPUT_OUTPUT_SLOT,inputStack);
        this.removeStack(MATERIAL_SLOT,1);
    }

    private boolean hasRecipe() {
        ItemStack input = getStack(INPUT_OUTPUT_SLOT);
        ItemStack ink = getStack(INK_SLOT);
        ItemStack material = getStack(MATERIAL_SLOT);
        NbtCompound nbtData = input.getOrCreateNbt();

        return input.getItem() == ModItems.SPELL_BOOK && ink.getItem() == Items.INK_SAC && possibleMaterials.contains(material.getItem()) && nbtData.getInt("mana")+40 < max_mana;
    }
}
