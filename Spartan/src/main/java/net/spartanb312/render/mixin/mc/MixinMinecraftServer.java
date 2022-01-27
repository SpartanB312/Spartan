package net.spartanb312.render.mixin.mc;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.WorldServer;
import net.spartanb312.render.features.event.world.WorldEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @Redirect(method = "loadAllWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;addEventListener(Lnet/minecraft/world/IWorldEventListener;)V"))
    public void onLoadWorlds(WorldServer instance, IWorldEventListener iWorldEventListener) {
        instance.addEventListener(iWorldEventListener);
        WorldEvent.postLoad(instance);
    }

    @Redirect(method = "stopServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;flush()V"))
    public void onStopServer(WorldServer instance) {
        WorldEvent.postUnload(instance);
        instance.flush();
    }

}
