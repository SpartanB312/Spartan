package net.spartanb312.render.core.setting.impl.collections.maps

import net.spartanb312.render.core.setting.impl.collections.MapSetting

class StringToIntegerMapSetting(
    name: String,
    value: Map<String, Int>,
    description: String = "",
    visibility: () -> Boolean = { true }
) : MapSetting<String, Int>(name, value, description, visibility) {

    override fun getStringMap(): Map<String, String> = value.mapValues {
        it.value.toString()
    }

    override fun readStringMap(stringMap: Map<String, String>) = stringMap.mapValues {
        it.value.toInt()
    }.let { value = it }

}