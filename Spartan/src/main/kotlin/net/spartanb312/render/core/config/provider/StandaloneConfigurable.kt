package net.spartanb312.render.core.config.provider

import net.spartanb312.render.core.config.impl.StandaloneConfig
import net.spartanb312.render.core.setting.AbstractSetting

open class StandaloneConfigurable(
    path: String,
    name: String,
) : IStandaloneConfigManagerProvider<StandaloneConfigurable> {
    override val containerName = name
    override val settings = mutableListOf<AbstractSetting<*>>()
    final override val configManager: StandaloneConfig = StandaloneConfig(path, this)
}