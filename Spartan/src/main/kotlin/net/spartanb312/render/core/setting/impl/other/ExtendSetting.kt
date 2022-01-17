package net.spartanb312.render.core.setting.impl.other

import net.spartanb312.render.core.setting.Convertable
import net.spartanb312.render.core.setting.MutableSetting

open class ExtendSetting<T : Convertable<T>>(
    name: String,
    value: T,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : MutableSetting<T>(name, value, description, visibility) {

    fun convertToString(): String = value.converter(value)

    fun parseString(string: String): T = value.parser(string)

    fun parseStringAndSet(string: String) {
        value = parseString(string)
    }

}