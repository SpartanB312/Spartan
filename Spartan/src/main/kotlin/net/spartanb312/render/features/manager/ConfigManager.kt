package net.spartanb312.render.features.manager

import net.spartanb312.render.core.config.Config
import net.spartanb312.render.core.config.provider.IStandaloneConfigManagerProvider
import net.spartanb312.render.features.language.LanguageManager

object ConfigManager {

    private val otherConfigs = mutableListOf<Config>()

    @JvmStatic
    fun IStandaloneConfigManagerProvider<*>.registerConfigs() = otherConfigs.add(configManager)

    @JvmStatic
    fun Config.registerConfigs() = otherConfigs.add(this)

    /**
     * Path of configs
     */
    @JvmStatic
    fun redirectPath(path: String) {
        ModuleManager.modules.forEach { it.redirect(path) }
        otherConfigs.forEach { it.redirect(path) }
    }

    @JvmStatic
    fun resetPath() {
        ModuleManager.modules.forEach { it.resetPath() }
        otherConfigs.forEach { it.resetPath() }
    }

    /**
     * Load config
     */
    @JvmStatic
    fun loadAll() {
        loadModuleConfigs()
        loadOtherConfigs()
        loadLanguages()
    }

    @JvmStatic
    fun loadModuleConfigs() = ModuleManager.modules.forEach { it.loadConfig() }

    @JvmStatic
    fun loadHUDsConfigs() = ModuleManager.HUDs.forEach { it.loadConfig() }

    @JvmStatic
    fun loadRenderersConfigs() = ModuleManager.renderers.forEach { it.loadConfig() }

    @JvmStatic
    fun loadUtilitiesConfigs() = ModuleManager.utilities.forEach { it.loadConfig() }

    @JvmStatic
    fun loadSettingConfigs() = ModuleManager.settings.forEach { it.loadConfig() }

    @JvmStatic
    fun loadOtherConfigs() = otherConfigs.forEach { it.loadConfig() }

    @JvmStatic
    fun loadLanguages() = LanguageManager.loadLanguages()

    /**
     * Save config
     */
    @JvmStatic
    fun saveAll() {
        saveModuleConfigs()
        saveOtherConfigs()
        saveLanguages()
    }

    @JvmStatic
    fun saveModuleConfigs() = ModuleManager.modules.forEach { it.saveConfig() }

    @JvmStatic
    fun saveHUDsConfigs() = ModuleManager.HUDs.forEach { it.saveConfig() }

    @JvmStatic
    fun saveRenderersConfigs() = ModuleManager.renderers.forEach { it.saveConfig() }

    @JvmStatic
    fun saveUtilitiesConfigs() = ModuleManager.utilities.forEach { it.saveConfig() }

    @JvmStatic
    fun saveSettingConfigs() = ModuleManager.settings.forEach { it.saveConfig() }

    @JvmStatic
    fun saveOtherConfigs() = otherConfigs.forEach { it.saveConfig() }

    @JvmStatic
    fun saveLanguages() = LanguageManager.saveLanguages()

    /**
     * Reset config
     */
    @JvmStatic
    fun resetAll() {
        resetModuleConfigs()
        resetOtherConfigs()
        resetLanguages()
    }

    @JvmStatic
    fun resetModuleConfigs() = ModuleManager.modules.forEach { it.resetConfig() }

    @JvmStatic
    fun resetHUDsConfigs() = ModuleManager.HUDs.forEach { it.resetConfig() }

    @JvmStatic
    fun resetRenderersConfigs() = ModuleManager.renderers.forEach { it.resetConfig() }

    @JvmStatic
    fun resetUtilitiesConfigs() = ModuleManager.utilities.forEach { it.resetConfig() }

    @JvmStatic
    fun resetSettingConfigs() = ModuleManager.settings.forEach { it.resetConfig() }

    @JvmStatic
    fun resetOtherConfigs() = otherConfigs.forEach { it.resetConfig() }

    @JvmStatic
    fun resetLanguages() = LanguageManager.resetLanguages()

}