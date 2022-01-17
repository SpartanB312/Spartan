package net.spartanb312.render.launch

import net.spartanb312.render.launch.mod.LoadableMod
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.MixinEnvironment
import org.spongepowered.asm.mixin.Mixins

object MixinLoader {

    fun Set<LoadableMod>.loadMixins() {
        MixinBootstrap.init()
        forEach {
            if (it.mixinFile != "") {
                Logger.info("Initializing mixin ${it.mixinFile}")
                Mixins.addConfiguration(it.mixinFile)
            }
        }
        MixinEnvironment.getDefaultEnvironment().obfuscationContext = "searge"
        MixinEnvironment.getDefaultEnvironment().side = MixinEnvironment.Side.CLIENT
        Logger.info("Spartan MixinLoader all mixins initialized")
        Logger.info(MixinEnvironment.getDefaultEnvironment().obfuscationContext)
    }

}