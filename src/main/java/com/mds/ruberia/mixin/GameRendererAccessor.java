package com.mds.ruberia.mixin;

import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface GameRendererAccessor {

    @Accessor("postProcessor")
    void setPostProcessor(PostEffectProcessor postProcessor);

    @Accessor("postProcessorEnabled")
    void setPostProcessorEnabled(boolean postProcessorEnabled);

    @Accessor("superSecretSettingIndex")
    void setSuperSecretSettingIndex(int superSecretSettingIndex);

    @Accessor
    int getSUPER_SECRET_SETTING_COUNT();
}
