package net.spartanb312.render.features.manager

import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.features.hud.information.TestHUD
import net.spartanb312.render.features.render.RenderModule
import net.spartanb312.render.launch.Logger

object ModuleManager {

    val modules = mutableSetOf<AbstractModule>()
    val HUDs = mutableSetOf<HUDModule>()
    val renders = mutableSetOf<RenderModule>()

    init {
        register(TestHUD)


        Render2DManager.register(HUDs)
    }

    fun register(module: AbstractModule) {
        Logger.error("Registered module ${module.name}")
        modules.add(module)
        when (module) {
            is HUDModule -> {
                HUDs.add(module)
            }
            is RenderModule -> {
                renders.add(module)
            }
        }
    }

    fun loadModuleConfigs() = modules.forEach { it.loadConfig() }

    fun saveModuleConfigs() = modules.forEach { it.saveConfig() }


}