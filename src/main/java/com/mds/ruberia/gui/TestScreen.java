package com.mds.ruberia.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.inventory.Inventory;
import net.minecraft.text.Text;


@Environment(EnvType.CLIENT)
public class TestScreen extends Screen {

    Screen parent;

    Inventory inventory;

    public ButtonWidget button1;



    public TestScreen(Screen parent, Inventory inventory) {
        super(Text.literal("test screen"));
        this.parent = parent;
        this.inventory = inventory;
    }


    @Override
    protected void init() {
        button1 = ButtonWidget.builder(Text.literal("Button 1"), button -> {

                })
                .dimensions(width / 2 - 180, 20, 200, 20)
                .tooltip(Tooltip.of(Text.literal("Tooltip of button1")))
                .build();

        addDrawableChild(button1);

    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(parent);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawItemInSlot(textRenderer,inventory.getStack(0),width/2,height/2);
        context.drawItem(inventory.getStack(0),width/2,height/2);

        super.render(context, mouseX, mouseY, delta);
    }


}