package net.spartanb312.render.core.setting.impl.collections.lists

import net.spartanb312.render.core.setting.impl.collections.ListSetting

class FloatListSetting(
    name: String,
    value: List<Float>,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : ListSetting<Float>(name, value, description, visibility)