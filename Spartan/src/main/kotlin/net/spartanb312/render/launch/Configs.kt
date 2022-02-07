package net.spartanb312.render.launch

import net.spartanb312.render.Spartan.DEFAULT_FILE_GROUP
import net.spartanb312.render.core.config.provider.StandaloneConfigurable

/**
 * Global config for spartan
 */
object Configs : StandaloneConfigurable(DEFAULT_FILE_GROUP, "Configs") {

    val compatibilityMode by setting("Compatibility Mode", true)
    val extensions by setting("Extensions", true)

}