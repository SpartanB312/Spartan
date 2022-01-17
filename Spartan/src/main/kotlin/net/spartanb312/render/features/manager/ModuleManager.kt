package net.spartanb312.render.features.manager

import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.features.render.RenderModule

object ModuleManager {

    val modules = mutableSetOf<AbstractModule>()
    val HUDs = mutableSetOf<HUDModule>()
    val renders = mutableSetOf<RenderModule>()

    fun register(module: AbstractModule) {
        modules.add(module)
        if (module is HUDModule) HUDs.add(module)
    }

    fun loadModuleConfigs() = modules.forEach { it.loadConfig() }

    fun saveModuleConfigs() = modules.forEach { it.saveConfig() }


}