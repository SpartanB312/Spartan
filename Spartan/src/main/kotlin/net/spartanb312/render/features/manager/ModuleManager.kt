package net.spartanb312.render.features.manager

import net.spartanb312.render.core.common.ClassUtils.findTypedClasses
import net.spartanb312.render.core.common.ClassUtils.instance
import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.common.AsyncLoadable
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.features.render.RenderModule
import net.spartanb312.render.features.setting.SettingModule
import net.spartanb312.render.features.utility.UtilityModule
import net.spartanb312.render.launch.Logger

object ModuleManager : AsyncLoadable {

    val modules = mutableListOf<AbstractModule>()
    val HUDs = mutableListOf<HUDModule>()
    val renderers = mutableListOf<RenderModule>()
    val utilities = mutableListOf<UtilityModule>()
    val settings = mutableListOf<SettingModule>()

    override suspend fun init() {
        Logger.info("Initializing ModuleManager")
        AsyncLoadable.classes.await().findTypedClasses<AbstractModule>().forEach {
            it.instance?.let { instance -> register(instance) }
        }
    }

    override fun postInit() {
        AsyncLoadable.glContextRequiredClasses.findTypedClasses<AbstractModule>().forEach {
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
                utilities.add(module)
                modules.add(module)
            }
            is SettingModule -> {
                settings.add(module)
                modules.add(module)
            }
            else -> modules.add(module)
        }
    }

    fun getModule(name: String): AbstractModule? = modules.find { it.name.equals(name, true) }

    fun getHUD(name: String): HUDModule? = HUDs.find { it.name.equals(name, true) }

    fun getRenderer(name: String): RenderModule? = renderers.find { it.name.equals(name, true) }

    fun getUtility(name: String): UtilityModule? = utilities.find { it.name.equals(name, true) }

    fun getSetting(name: String): SettingModule? = settings.find { it.name.equals(name, true) }

}