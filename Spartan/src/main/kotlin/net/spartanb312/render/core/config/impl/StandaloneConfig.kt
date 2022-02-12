package net.spartanb312.render.core.config.impl

import com.google.gson.JsonObject
import net.spartanb312.render.core.config.Config
import net.spartanb312.render.core.config.IConfigContainer
import net.spartanb312.render.core.config.readSettings
import net.spartanb312.render.core.config.saveSettings

class StandaloneConfig(
    dirPath: String = "config",
    val container: IConfigContainer<*>
) : Config(dirPath, container.containerName) {

    override fun saveConfig() {
        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()
            configFile.createNewFile()
        }
        JsonObject().saveSettings(container.settings).saveToFile()
    }

    override fun loadConfig() {
        if (configFile.exists()) {
            configFile.jsonMap.readSettings(container.settings)
        } else saveConfig()
    }

    override fun resetConfig() {
        container.settings.forEach {
            it.reset()
        }
        saveConfig()
    }

}