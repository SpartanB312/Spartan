package net.spartanb312.render.core.setting.impl.collections.maps

import net.spartanb312.render.core.setting.impl.collections.MapSetting

class StringToLongMapSetting(
    name: String,
    value: Map<String, Long>,
    description: String = "",
    visibility: () -> Boolean = { true }
) : MapSetting<String, Long>(name, value, description, visibility) {

    override fun getStringMap(): Map<String, String> = value.mapValues {
        it.value.toString()
    }

    override fun readStringMap(stringMap: Map<String, String>) = stringMap.mapValues {
        it.value.toLong()
    }.let { value = it }

}