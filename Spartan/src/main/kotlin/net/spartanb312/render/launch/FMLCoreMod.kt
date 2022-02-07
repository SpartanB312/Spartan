package net.spartanb312.render.launch

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
import net.spartanb312.render.launch.transform.SpartanClassTransformer

class FMLCoreMod : IFMLLoadingPlugin {

    init {
        Configs.loadConfig()
        Logger.info("Loading Spartan InitializationManager")
        InitializationManager.init()
        Logger.info("Tweaking Spartan Mods")
        InitializationManager.onTweak()
    }

    override fun getASMTransformerClass(): Array<String> {
        return arrayOf(SpartanClassTransformer::class.java.name)
    }

    override fun getModContainerClass(): String? {
        return null
    }

    override fun getSetupClass(): String? {
        return null
    }

    override fun injectData(data: Map<String, Any>) {
    }

    override fun getAccessTransformerClass(): String? {
        return null
    }

}