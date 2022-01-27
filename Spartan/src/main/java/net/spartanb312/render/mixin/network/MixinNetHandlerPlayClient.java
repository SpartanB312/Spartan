package net.spartanb312.render.mixin.network;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.spartanb312.render.features.event.world.WorldEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

    @Shadow
    private WorldClient world;

    @Inject(method = "handleRespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;setWorldScoreboard(Lnet/minecraft/scoreboard/Scoreboard;)V", shift = At.Shift.BEFORE))
    public void onRespawn(CallbackInfo callbackInfo) {
        WorldEvent.postLoad(world);
    }

    @Inject(method = "handleJoinGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/server/SPacketJoinGame;getDifficulty()Lnet/minecraft/world/EnumDifficulty;", shift = At.Shift.BEFORE,ordinal = 1))
    public void onJoinGame(CallbackInfo callbackInfo) {
        WorldEvent.postLoad(world);
    }

}
