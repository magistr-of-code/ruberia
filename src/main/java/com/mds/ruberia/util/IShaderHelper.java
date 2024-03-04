package com.mds.ruberia.util;

import com.google.gson.JsonSyntaxException;
import com.mds.ruberia.mixin.GameRendererAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.Objects;

public interface IShaderHelper {
    static void loadShader(Identifier SHADER, MinecraftClient clientInstance){
        GameRendererAccessor gameRenderer = ((GameRendererAccessor) clientInstance.gameRenderer);

        if (clientInstance.gameRenderer.getPostProcessor() != null) {
            Objects.requireNonNull(clientInstance.gameRenderer.getPostProcessor()).close();
        } else {
            try {
                PostEffectProcessor effect = new PostEffectProcessor(clientInstance.getTextureManager(), clientInstance.getResourceManager(),clientInstance.getFramebuffer(), SHADER);
                gameRenderer.setPostProcessor(effect);
                clientInstance.gameRenderer.getPostProcessor().setupDimensions(clientInstance.getWindow().getFramebufferWidth(), clientInstance.getWindow().getFramebufferHeight());
                gameRenderer.setPostProcessorEnabled(true);
            } catch (IOException var3) {
                System.out.println("var3");
                gameRenderer.setSuperSecretSettingIndex(gameRenderer.getSUPER_SECRET_SETTING_COUNT());
                gameRenderer.setPostProcessorEnabled(false);
            } catch (JsonSyntaxException var4) {
                System.out.println("var4");
                gameRenderer.setSuperSecretSettingIndex(gameRenderer.getSUPER_SECRET_SETTING_COUNT());
                gameRenderer.setPostProcessorEnabled(false);
            }
        }
    }

    static void loadShader(PostEffectProcessor effectProcessor, MinecraftClient clientInstance){
        GameRendererAccessor gameRenderer = ((GameRendererAccessor) clientInstance.gameRenderer);

        if (clientInstance.gameRenderer.getPostProcessor() != null) {
            Objects.requireNonNull(clientInstance.gameRenderer.getPostProcessor()).close();
        } else {
            try {
                gameRenderer.setPostProcessor(effectProcessor);
                clientInstance.gameRenderer.getPostProcessor().setupDimensions(clientInstance.getWindow().getFramebufferWidth(), clientInstance.getWindow().getFramebufferHeight());
                gameRenderer.setPostProcessorEnabled(true);
            } catch (JsonSyntaxException var4) {
                System.out.println("var4");
                gameRenderer.setSuperSecretSettingIndex(gameRenderer.getSUPER_SECRET_SETTING_COUNT());
                gameRenderer.setPostProcessorEnabled(false);
            }
        }
    }

}
