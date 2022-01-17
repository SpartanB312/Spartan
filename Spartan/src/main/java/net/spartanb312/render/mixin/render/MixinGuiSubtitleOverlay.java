package net.spartanb312.render.mixin.render;

import net.minecraft.client.gui.GuiSubtitleOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.spartanb312.render.core.event.inner.MainEventBus;
import net.spartanb312.render.features.event.render.Render2DEvent;
import net.spartanb312.render.features.manager.ResolutionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSubtitleOverlay.class)
public class MixinGuiSubtitleOverlay {

    @Inject(method = "renderSubtitles", at = @At("HEAD"))
    public void onRender2D(ScaledResolution resolution, CallbackInfo ci) {
        ResolutionHelper.update(resolution);
        MainEventBus.INSTANCE.post(new Render2DEvent(resolution));
    }

}
