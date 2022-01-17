package net.spartanb312.render.core.setting.impl.collections.maps

import net.spartanb312.render.core.setting.impl.collections.MapSetting

class StringToDoubleMapSetting(
    name: String,
    value: Map<String, Double>,
    description: String = "",
    visibility: () -> Boolean = { true }
) : MapSetting<String, Double>(name, value, description, visibility) {

    override fun getStringMap(): Map<String, String> = value.mapValues {
        it.value.toString()
    }

    override fun readStringMap(stringMap: Map<String, String>) = stringMap.mapValues {
        it.value.toDouble()
    }.let { value = it }

}