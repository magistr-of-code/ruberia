package com.mds.ruberia.screen;

import com.mds.ruberia.Ruberia;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {

    public static final ScreenHandlerType<WritingTableScreenHandler> WRITING_TABLE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER,new Identifier(Ruberia.MOD_ID,"writing_table"),
                    new ExtendedScreenHandlerType<>(WritingTableScreenHandler::new));

    public static void registerScreenHandlers(){
        Ruberia.LOGGER.info("Registering Screen Handlers for " + Ruberia.MOD_ID);
    }
}
