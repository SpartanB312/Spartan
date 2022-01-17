package net.spartanb312.render.mixin.mc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.spartanb312.render.features.event.client.GameLoopEvent;
import net.spartanb312.render.features.event.client.TickEvent;
import net.spartanb312.render.features.manager.InputManager;
import net.spartanb312.render.features.manager.MainThreadExecutor;
import net.spartanb312.render.launch.InitializationManager;
import net.spartanb312.render.util.WrapperKt;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow
    public GuiScreen currentScreen;

    @Inject(method = "runTick", at = @At("HEAD"))
    private void onRunTickPre(CallbackInfo ci) {
        MainThreadExecutor.INSTANCE.runJobs();
        TickEvent.Client.Pre.INSTANCE.post(WrapperKt.getSafeCheck());
    }

    @Inject(method = "runTick", at = @At("RETURN"))
    private void onRunTickPost(CallbackInfo ci) {
        MainThreadExecutor.INSTANCE.runJobs();
        TickEvent.Client.Post.INSTANCE.post(WrapperKt.getSafeCheck());
    }

    @Inject(method = "runGameLoop", at = @At("HEAD"))
    private void onRunningGameLoopHead(CallbackInfo ci) {
        MainThreadExecutor.INSTANCE.runJobs();
        GameLoopEvent.Pre.INSTANCE.post();
    }

    @Inject(method = "runGameLoop", at = @At("RETURN"))
    private void onRunningGameLoopReturn(CallbackInfo ci) {
        MainThreadExecutor.INSTANCE.runJobs();
        GameLoopEvent.Post.INSTANCE.post();
    }

    @Inject(method = "init", at = @At("HEAD"))
    private void onPreInit(CallbackInfo ci) {
        InitializationManager.onPreInit();
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 1, shift = At.Shift.AFTER))
    private void onInit(CallbackInfo ci) {
        InitializationManager.onInit();
    }

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 2, shift = At.Shift.AFTER))
    private void onPostInit(CallbackInfo ci) {
        InitializationManager.onPostInit();
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void onReady(CallbackInfo ci) {
        InitializationManager.onReady();
    }

    @Inject(method = "runTickKeyboard", at = @At(value = "INVOKE_ASSIGN", target = "org/lwjgl/input/Keyboard.getEventKeyState()Z", remap = false))
    private void onKeyEvent(CallbackInfo ci) {
        if (currentScreen != null) return;

        int key = Keyboard.getEventKey();
        if (key != Keyboard.KEY_NONE && Keyboard.getEventKeyState()) InputManager.onKey(key);
    }

}