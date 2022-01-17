package net.spartanb312.render.core.setting.impl.collections.lists

import net.spartanb312.render.core.setting.impl.collections.ListSetting

class DoubleListSetting(
    name: String,
    value: List<Double>,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : ListSetting<Double>(name, value, description, visibility)
