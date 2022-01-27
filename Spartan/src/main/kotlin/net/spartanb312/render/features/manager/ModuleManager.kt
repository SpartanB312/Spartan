package net.spartanb312.render.features.manager

import net.spartanb312.render.core.common.ClassUtils.findTypedClasses
import net.spartanb312.render.core.common.ClassUtils.instance
import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.features.render.RenderModule
import net.spartanb312.render.launch.InitializationManager.DEFAULT_INIT_SCAN_EXCLUSION
import net.spartanb312.render.launch.InitializationManager.SCAN_GROUP
import net.spartanb312.render.launch.Logger

object ModuleManager {

    val modules = mutableListOf<AbstractModule>()
    val HUDs = mutableListOf<HUDModule>()
    val renderers = mutableListOf<RenderModule>()

    init {
        Logger.info("Initializing ModuleManager")
        SCAN_GROUP.findTypedClasses<AbstractModule>(DEFAULT_INIT_SCAN_EXCLUSION).forEach {
            it.instance?.let { instance -> register(instance) }
        }

        modules.sortBy { it.name }
        HUDs.sortBy { it.name }
        renderers.sortBy { it.name }

        Render2DManager.register(HUDs)
    }

    fun register(module: AbstractModule) {
        modules.add(module)
        when (module) {
            is HUDModule -> {
                HUDs.add(module)
            }
            is RenderModule -> {
                renderers.add(module)
            }
        }
    }


}