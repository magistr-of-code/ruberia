package com.mds.ruberia.item.custom.spell_casting_items;

public class SpellBook extends SpellCastingItem {

    public SpellBook(Settings settings) {
        super(settings);
    }

    @Override
    public double getManaMultiplier() {
        return 20;
    }
}
