package net.spartanb312.render.mixin.world;

import net.minecraft.client.multiplayer.WorldClient;
import net.spartanb312.render.features.event.network.ConnectionEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public class MixinWorldClient {

    @Inject(method = "sendQuittingDisconnectingPacket", at = @At("HEAD"))
    private void onPreSendQuittingDisconnectingPacket(CallbackInfo ci) {
        ConnectionEvent.Disconnect.INSTANCE.post();
    }

}
