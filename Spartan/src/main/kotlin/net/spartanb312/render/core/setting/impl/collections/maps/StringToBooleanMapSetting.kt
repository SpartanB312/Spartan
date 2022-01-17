package net.spartanb312.render.core.setting.impl.collections.maps

import net.spartanb312.render.core.setting.impl.collections.MapSetting

class StringToBooleanMapSetting(
    name: String,
    value: Map<String, Boolean>,
    description: String = "",
    visibility: () -> Boolean = { true },
) : MapSetting<String, Boolean>(name, value, description, visibility) {

    override fun getStringMap(): Map<String, String> = value.mapValues {
        it.value.toString()
    }

    override fun readStringMap(stringMap: Map<String, String>) = stringMap.mapValues {
        it.value.toBoolean()
    }.let { value = it }

}