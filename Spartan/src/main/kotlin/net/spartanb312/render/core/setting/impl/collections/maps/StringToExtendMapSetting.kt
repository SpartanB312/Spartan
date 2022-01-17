package net.spartanb312.render.core.setting.impl.collections.maps

import net.spartanb312.render.core.setting.Convertable
import net.spartanb312.render.core.setting.impl.collections.MapSetting

class StringToExtendMapSetting<K : Convertable<K>>(
    name: String,
    value: Map<String, K>,
    description: String = "",
    val parser: String.() -> K,
    visibility: () -> Boolean = { true }
) : MapSetting<String, K>(name, value, description, visibility) {

    override fun getStringMap(): Map<String, String> = value.mapValues {
        it.value.converter(it.value)
    }

    override fun readStringMap(stringMap: Map<String, String>) = stringMap.mapValues {
        it.value.parser()
    }.let { value = it }

}