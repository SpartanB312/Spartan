package net.spartanb312.render.graphics.impl.structure.misc

import net.spartanb312.render.core.setting.AbstractSetting

/**
 * Contains a setting(For setting buttons)
 */
interface SettingSupplier<T> {
    val setting: AbstractSetting<T>
}