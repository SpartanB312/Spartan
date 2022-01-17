package net.spartanb312.render.core.setting.impl.collections

import net.spartanb312.render.core.setting.MutableSetting

open class ListSetting<T>(
    name: String,
    value: List<T>,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : MutableSetting<List<T>>(name, value, description, visibility)