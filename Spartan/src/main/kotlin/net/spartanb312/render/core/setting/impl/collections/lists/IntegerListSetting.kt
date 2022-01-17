package net.spartanb312.render.core.setting.impl.collections.lists

import net.spartanb312.render.core.setting.impl.collections.ListSetting

class IntegerListSetting(
    name: String,
    value: List<Int>,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : ListSetting<Int>(name, value, description, visibility)