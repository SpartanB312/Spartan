package net.spartanb312.render.mixin.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.spartanb312.render.features.manager.SoundManager;
import net.spartanb312.render.features.ui.DisplayManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTicker.class)
public class MixinMusicTicker {

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void onUpdate(CallbackInfo ci) {
        if (Minecraft.getMinecraft().currentScreen == DisplayManager.INSTANCE.getScreen()) {
            ci.cancel();
            SoundManager.update();
        }
    }

}
