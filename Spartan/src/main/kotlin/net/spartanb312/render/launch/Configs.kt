package net.spartanb312.render.launch

import net.spartanb312.render.Spartan.DEFAULT_FILE_GROUP
import net.spartanb312.render.core.config.provider.StandaloneConfigurable

/**
 * Global config for spartan
 */
object Configs : StandaloneConfigurable(DEFAULT_FILE_GROUP, "Configs") {

    private val compatibilityMode by setting("Compatibility Mode", true)
    private val fastBoot by setting("Fast boot", true)
    private val extensions by setting("Extensions Support", true)
    val autoRegister by setting("AutoRegister", false)

    val isCompatibilityMode get() = compatibilityMode
    val isFastBoot get() = compatibilityMode || fastBoot
    val loadExtensions get() = compatibilityMode || extensions

}