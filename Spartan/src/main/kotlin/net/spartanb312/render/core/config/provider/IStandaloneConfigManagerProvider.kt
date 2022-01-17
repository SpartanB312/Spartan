package net.spartanb312.render.core.config.provider

import net.spartanb312.render.core.config.IConfigContainer
import net.spartanb312.render.core.config.impl.StandaloneConfig

interface IStandaloneConfigManagerProvider<T> : IConfigContainer<T> {

    val configManager: StandaloneConfig

    fun String.create(): StandaloneConfig {
        return StandaloneConfig(this, this@IStandaloneConfigManagerProvider)
    }

}