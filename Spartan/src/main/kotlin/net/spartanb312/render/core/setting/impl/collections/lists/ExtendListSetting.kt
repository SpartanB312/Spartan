package net.spartanb312.render.core.setting.impl.collections.lists

import net.spartanb312.render.core.setting.Convertable
import net.spartanb312.render.core.setting.impl.collections.ListSetting

class ExtendListSetting<T : Convertable<T>>(
    name: String,
    value: List<T>,
    description: String = "",
    val parser: String.() -> T,
    visibility: (() -> Boolean) = { true }
) : ListSetting<T>(name, value, description, visibility) {

    fun convertToStringList(): List<String> {
        val list = mutableListOf<String>()
        value.forEach {
            list.add(it.converter(it))
        }
        return list
    }

    fun parseStringList(strings: List<String>): List<T> {
        val list = mutableListOf<T>()
        strings.forEach {
            list.add(parser(it))
        }
        return list
    }

}