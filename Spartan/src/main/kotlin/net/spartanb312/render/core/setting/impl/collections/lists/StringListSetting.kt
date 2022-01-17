package net.spartanb312.render.core.setting.impl.collections.lists

import net.spartanb312.render.core.setting.impl.collections.ListSetting

class StringListSetting(
    name: String,
    value: List<String>,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : ListSetting<String>(name, value, description, visibility)
