package com.mds.ruberia.screen;

import com.mds.ruberia.block.entity.WritingTableBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.Objects;

public class WritingTableScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public final WritingTableBlockEntity blockEntity;

    public WritingTableScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),new ArrayPropertyDelegate(3));
    }

    public WritingTableScreenHandler(int syncId, PlayerInventory PlayerInventory, BlockEntity blockEntity, PropertyDelegate PropertyDelegate) {
        super(ModScreenHandlers.WRITING_TABLE_SCREEN_HANDLER,syncId);
        checkSize(((Inventory) blockEntity),3);
        this.inventory = ((Inventory) blockEntity);
        inventory.onOpen(PlayerInventory.player);
        this.propertyDelegate = PropertyDelegate;
        this.blockEntity = ((WritingTableBlockEntity) blockEntity);

        this.addSlot(new Slot(inventory,0,80,11));
        this.addSlot(new Slot(inventory,1,80,59));
        this.addSlot(new Slot(inventory,2,122,11));

        addPlayerInventory(PlayerInventory);
        addPlayerHotbar(PlayerInventory);
    }

    public int getScaledMana() {
        int activeSpells = 0;
        if (blockEntity.getStack(WritingTableBlockEntity.INPUT_OUTPUT_SLOT).getNbt() != null) {
            activeSpells = Objects.requireNonNull(blockEntity.getStack(WritingTableBlockEntity.INPUT_OUTPUT_SLOT).getNbt()).getInt("active_spells");
        } //else{System.out.println("null");}
        int maxSpells = this.propertyDelegate.get(0);  // Max Progress
        int manaArrowSize = 26; // This is the width in pixels of your arrow

        return maxSpells != 0 && activeSpells != 0 ? activeSpells * manaArrowSize / activeSpells : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
