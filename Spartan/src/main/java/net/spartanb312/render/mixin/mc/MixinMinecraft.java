package net.spartanb312.render.mixin.mc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.spartanb312.render.features.event.client.GameLoopEvent;
import net.spartanb312.render.features.event.client.TickEvent;
import net.spartanb312.render.features.event.world.WorldEvent;
import net.spartanb312.render.features.manager.InputManager;
import net.spartanb312.render.features.manager.MainThreadExecutor;
import net.spartanb312.render.features.setting.ui.Background;
import net.spartanb312.render.features.ui.DisplayManager;
import net.spartanb312.render.features.ui.wrapper.DelegateRenderer;
import net.spartanb312.render.launch.InitializationManager;
import net.spartanb312.render.util.WrapperKt;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Shadow
    public GuiScreen currentScreen;

    @Shadow
    public WorldClient world;

    @Shadow
    public boolean skipRenderWorld;

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

    @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
    public void onLoadWorld(WorldClient authenticationservice, String minecraftsessionservice, CallbackInfo ci) {
        if (world != null) WorldEvent.postUnload(world);
    }

    @Inject(method = "getLimitFramerate", at = @At("HEAD"), cancellable = true)
    public void getLimitFramerate$Inject$HEAD(CallbackInfoReturnable<Integer> cir) {
        if (WrapperKt.getMc().world == null && WrapperKt.getMc().currentScreen != null) {
            cir.setReturnValue(Background.INSTANCE.getFpsLimit());
        }
    }

    @Inject(method = "displayGuiScreen", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;", shift = At.Shift.AFTER))
    private void displayGuiScreen(CallbackInfo callbackInfo) {
        if (currentScreen instanceof net.minecraft.client.gui.GuiMainMenu || (currentScreen != null && currentScreen.getClass().getName().startsWith("net.labymod") && currentScreen.getClass().getSimpleName().equals("ModGuiMainMenu"))) {
            currentScreen = DisplayManager.INSTANCE.getScreen().set(new DelegateRenderer(DisplayManager.Renderers.MENU_GATE, null));
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            currentScreen.setWorldAndResolution(Minecraft.getMinecraft(), scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight());
            skipRenderWorld = false;
        }
    }

}