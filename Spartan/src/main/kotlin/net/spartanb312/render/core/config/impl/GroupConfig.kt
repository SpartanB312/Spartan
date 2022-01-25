package net.spartanb312.render.core.config.impl

import com.google.gson.JsonObject
import net.spartanb312.render.core.config.Config
import net.spartanb312.render.core.config.IConfigContainer
import net.spartanb312.render.core.config.readSettings
import net.spartanb312.render.core.config.saveSettings

class GroupConfig(
    dirPath: String = "config",
    configName: String,
    private val containers: List<IConfigContainer<*>>
) : Config(dirPath, configName) {

    override fun saveConfig() {
        if (!configFile.exists()) {
            configFile.parentFile.mkdirs()
            configFile.createNewFile()
        }
        JsonObject().apply {
            containers.forEach {
                add(it.containerName, JsonObject().saveSettings(it.settings))
            }
        }.saveToFile()
    }

    override fun loadConfig() {
        if (configFile.exists()) {
            val map = jsonMap
            containers.forEach {
                map[it.containerName]?.asJsonObject?.readSettings(it.settings)
            }
        } else saveConfig()
    }

    override fun resetConfig() {
        containers.forEach { container ->
            container.settings.forEach {
                it.reset()
            }
        }
        saveConfig()
    }

}