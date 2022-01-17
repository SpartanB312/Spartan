package net.spartanb312.render.core.setting.impl.primitive

import net.spartanb312.render.core.common.extension.last
import net.spartanb312.render.core.common.extension.next
import net.spartanb312.render.core.setting.MutableSetting

class EnumSetting<T : Enum<T>>(
    name: String,
    value: T,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : MutableSetting<T>(name, value, description, visibility) {

    private val enumClass: Class<T> = value.declaringClass
    private val enumValues: Array<out T> = enumClass.enumConstants

    fun nextValue() {
        value = value.next()
    }

    fun lastValue() {
        value = value.last()
    }

    fun currentName(): String {
        return value.name
    }

    fun setWithName(nameIn: String) {
        enumValues.firstOrNull { it.name.equals(nameIn, true) }?.let {
            value = it
        }
    }
}