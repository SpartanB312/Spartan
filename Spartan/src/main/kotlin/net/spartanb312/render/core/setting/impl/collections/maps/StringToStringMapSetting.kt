package net.spartanb312.render.core.setting.impl.collections.maps

import net.spartanb312.render.core.setting.impl.collections.MapSetting

class StringToStringMapSetting(
    name: String,
    value: Map<String, String>,
    description: String = "",
    visibility: () -> Boolean = { true }
) : MapSetting<String, String>(name, value, description, visibility) {

    override fun getStringMap(): Map<String, String> = value

    override fun readStringMap(stringMap: Map<String, String>) = stringMap.let { value = it }

}