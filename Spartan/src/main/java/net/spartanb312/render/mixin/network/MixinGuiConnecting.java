package net.spartanb312.render.mixin.network;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.spartanb312.render.features.event.network.ConnectionEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting {

    @Inject(method = "connect", at = @At("HEAD"))
    private void onPreConnect(CallbackInfo info) {
        ConnectionEvent.Connect.INSTANCE.post();
    }

}
