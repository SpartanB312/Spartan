package net.spartanb312.render.features.common

import net.spartanb312.render.core.common.KeyBind
import net.spartanb312.render.core.setting.AbstractSetting
import net.spartanb312.render.core.setting.impl.other.BindSetting
import net.spartanb312.render.features.manager.InputManager.register

@Suppress("LeakingThis")
abstract class AbstractModule(
    name: String,
    alias: Array<String>,
    category: Category,
    description: String,
    val priority: Int,
    keyCode: Int,
    visibility: Boolean,
) : AbstractFeature(name, alias, category, description), ToggleableFeature {

    private val defaultVisibility = if (visibility) Visibility.ON else Visibility.OFF

    /**
     * Implements ToggleableFeature
     */
    override var isEnabled = false

    /**
     * Some private properties
     */
    private val keyBind: KeyBind = KeyBind(keyCode, action = { toggle() }).apply { register() }
    private val reset: () -> Unit = { configManager.container.settings.forEach { it.reset() } }
    private val keyBinds = mutableSetOf<KeyBind>()

    /**
     * These are the default setting of any module that inherits from [AbstractModule]
     */
    private val visibilitySetting by setting("Visible", defaultVisibility, "The Visibility of this feature")
    private val keyBindSetting by setting("KeyBind", keyBind, "Bind a key to toggle this feature")
    private val onHold by setting("OnHold", false, "Feature functional while pressing the key")
    private val resetButton by setting("Reset", reset, "Click here to reset this feature")

    fun reset() = reset.invoke()

    override fun <S : AbstractSetting<*>> AbstractFeature.setting(setting: S): S = setting.also {
        settings.add(setting)
        if (setting is BindSetting) keyBinds.add(setting.value)
    }

    enum class Visibility { ON, OFF }

}