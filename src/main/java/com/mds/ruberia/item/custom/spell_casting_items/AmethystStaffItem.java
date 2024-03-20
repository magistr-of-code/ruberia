package com.mds.ruberia.item.custom.spell_casting_items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AmethystStaffItem extends SpellCastingItem {

    public AmethystStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(Text.translatable("tooltip.ruberia.amethyst_staff"));

        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public double getManaMultiplier() {
        return 5;
    }
}