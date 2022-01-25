package net.spartanb312.render.core.config.provider

import net.spartanb312.render.core.config.impl.StandaloneConfig
import net.spartanb312.render.core.setting.AbstractSetting
import net.spartanb312.render.features.manager.ConfigManager.register

@Suppress("LeakingThis")
open class StandaloneConfigurable(
    path: String,
    name: String,
    autoRegister: Boolean = true
) : IStandaloneConfigManagerProvider<StandaloneConfigurable> {
    override val containerName = name
    override val settings = mutableListOf<AbstractSetting<*>>()
    final override val configManager: StandaloneConfig = StandaloneConfig(path, this)

    init {
        if (autoRegister) this.register()
    }
}