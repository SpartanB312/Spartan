package net.spartanb312.render.features.manager

import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.features.hud.information.TestHUD
import net.spartanb312.render.features.render.RenderModule

object ModuleManager {

    val modules = mutableSetOf<AbstractModule>()
    val HUDs = mutableSetOf<HUDModule>()
    val renderers = mutableSetOf<RenderModule>()

    init {
        register(TestHUD)


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