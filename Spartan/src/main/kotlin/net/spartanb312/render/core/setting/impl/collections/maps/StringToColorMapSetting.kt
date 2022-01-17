package net.spartanb312.render.core.setting.impl.collections.maps

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.setting.impl.collections.MapSetting

class StringToColorMapSetting(
    name: String,
    value: Map<String, ColorRGB>,
    description: String = "",
    visibility: () -> Boolean = { true },
) : MapSetting<String, ColorRGB>(name, value, description, visibility) {

    override fun getStringMap(): Map<String, String> = value.mapValues {
        it.value.toString()
    }

    override fun readStringMap(stringMap: Map<String, String>) = stringMap.mapValues {
        ColorRGB(it.value.toInt())
    }.let { value = it }

}