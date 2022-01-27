package net.spartanb312.render.mixin.mc;

import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.WorldServer;
import net.spartanb312.render.features.event.world.WorldEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IntegratedServer.class)
public class MixinIntegratedServer {

    @Redirect(method = "loadAllWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;addEventListener(Lnet/minecraft/world/IWorldEventListener;)V"))
    public void onLoadAllWorlds(WorldServer instance, IWorldEventListener iWorldEventListener) {
        instance.addEventListener(iWorldEventListener);
        WorldEvent.postLoad(instance);
    }

}