package net.spartanb312.render.mixin.mc;

import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.spartanb312.render.features.event.world.WorldEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DimensionManager.class)
public class MixinDimensionManager {

    @Redirect(method = "initDimension", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;addEventListener(Lnet/minecraft/world/IWorldEventListener;)V"))
    private static void onInitDimension(WorldServer instance, IWorldEventListener iWorldEventListener) {
        instance.addEventListener(iWorldEventListener);
        WorldEvent.postLoad(instance);
    }

    @Redirect(method = "unloadWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;flush()V"))
    private static void onUnloadWorlds(WorldServer instance) {
        WorldEvent.postUnload(instance);
        instance.flush();
    }

}
