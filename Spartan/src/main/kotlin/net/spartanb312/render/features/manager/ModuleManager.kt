package net.spartanb312.render.features.manager

import net.spartanb312.render.Spartan
import net.spartanb312.render.core.common.ClassUtils.findTypedClasses
import net.spartanb312.render.core.common.ClassUtils.instance
import net.spartanb312.render.core.config.provider.StandaloneConfigurable
import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.common.AsyncLoadable
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.features.render.RenderModule
import net.spartanb312.render.features.utility.UtilityModule
import net.spartanb312.render.launch.Logger

object ModuleManager : StandaloneConfigurable(
    "${Spartan.DEFAULT_CONFIG_GROUP}/managers/",
    "ModuleManager"
), AsyncLoadable {

    private val enabledUtilities by setting("EnabledUtilities", true)

    val modules = mutableListOf<AbstractModule>()
    val HUDs = mutableListOf<HUDModule>()
    val renderers = mutableListOf<RenderModule>()
    val utilities = mutableListOf<UtilityModule>()

    override suspend fun init() {
        Logger.info("Initializing ModuleManager")
        AsyncLoadable.classes.await().findTypedClasses<AbstractModule>().forEach {
            it.instance?.let { instance -> register(instance) }
        }

        modules.sortBy { it.name }
        HUDs.sortBy { it.name }
        renderers.sortBy { it.name }
        utilities.sortBy { it.name }

        Render2DManager.register(HUDs)
    }

    fun register(module: AbstractModule) {
        when (module) {
            is HUDModule -> {
                HUDs.add(module)
                modules.add(module)
            }
            is RenderModule -> {
                renderers.add(module)
                modules.add(module)
            }
            is UtilityModule -> {
                if (enabledUtilities) {
                    utilities.add(module)
                    modules.add(module)
                }
            }
        }
    }

}