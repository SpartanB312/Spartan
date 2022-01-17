package net.spartanb312.render.mixin.render;

import net.minecraft.client.renderer.EntityRenderer;
import net.spartanb312.render.core.event.inner.MainEventBus;
import net.spartanb312.render.features.event.render.Render3DEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderer.class, priority = Integer.MAX_VALUE)
public class MixinEntityRenderer {

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;clear(I)V", ordinal = 1, shift = At.Shift.AFTER))
    private void onRenderWorldPassClear(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        MainEventBus.INSTANCE.post(new Render3DEvent(partialTicks));
    }

}
