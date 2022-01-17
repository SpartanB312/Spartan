package net.spartanb312.render.core.setting.impl.collections.lists

import net.spartanb312.render.core.setting.impl.collections.ListSetting

class LongListSetting(
    name: String,
    value: List<Long>,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : ListSetting<Long>(name, value, description, visibility)