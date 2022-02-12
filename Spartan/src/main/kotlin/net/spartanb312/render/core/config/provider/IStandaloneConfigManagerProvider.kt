package net.spartanb312.render.core.config.provider

import net.spartanb312.render.core.config.IConfigContainer
import net.spartanb312.render.core.config.impl.StandaloneConfig

interface IStandaloneConfigManagerProvider<T> : IConfigContainer<T> {

    val configManager: StandaloneConfig

    fun String.create(): StandaloneConfig {
        return StandaloneConfig(this, this@IStandaloneConfigManagerProvider)
    }

    fun loadConfig() = configManager.loadConfig()

    fun saveConfig() = configManager.saveConfig()

    fun resetConfig() = configManager.resetConfig()

    fun redirect(path: String) = configManager.redirect(path)

    fun resetPath() = configManager.resetPath()

}