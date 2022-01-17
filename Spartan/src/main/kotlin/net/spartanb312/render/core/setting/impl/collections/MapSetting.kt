package net.spartanb312.render.core.setting.impl.collections

import net.spartanb312.render.core.setting.MutableSetting

abstract class MapSetting<K, V>(
    name: String,
    value: Map<K, V>,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : MutableSetting<Map<K, V>>(name, value, description, visibility) {

    abstract fun getStringMap(): Map<String, String>

    abstract fun readStringMap(stringMap: Map<String, String>)

}