package com.mds.ruberia.event;

import com.mds.ruberia.mixin.GameRendererAccessor;
import com.mds.ruberia.networking.ModMessages;
import com.mds.ruberia.util.IShaderHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.Objects;

public class KeyInputHandler {

    public static KeyBinding activate_necklace_keybinding;
    public static KeyBinding test_keybinding;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (activate_necklace_keybinding.wasPressed()) {
                ClientPlayNetworking.send(ModMessages.ARTIFACT_ID, PacketByteBufs.create());
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (test_keybinding.wasPressed()) {
                try {
                    Identifier identifier = new Identifier("shaders/post/invert.json");
                    PostEffectProcessor effect = new PostEffectProcessor(
                            MinecraftClient.getInstance().getTextureManager(),
                            MinecraftClient.getInstance().getResourceManager(),
                            MinecraftClient.getInstance().getFramebuffer(),
                            identifier
                    );

                    if (!Objects.equals(MinecraftClient.getInstance().gameRenderer.getPostProcessor(), effect)) {
                        IShaderHelper.loadShader(identifier, MinecraftClient.getInstance());
                    } else {
                        GameRendererAccessor gameRenderer = ((GameRendererAccessor) MinecraftClient.getInstance().gameRenderer);
                        gameRenderer.setSuperSecretSettingIndex(gameRenderer.getSUPER_SECRET_SETTING_COUNT());
                        gameRenderer.setPostProcessorEnabled(false);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public static void register(){
         activate_necklace_keybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ruberia.activate_artifacts",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.ruberia.abilities"
        ));

        test_keybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ruberia.test_necklace",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "category.ruberia.test"
        ));

         registerKeyInputs();
    }
}
