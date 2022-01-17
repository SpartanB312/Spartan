package net.spartanb312.render.core.setting.impl.primitive

import net.spartanb312.render.core.setting.MutableSetting

class StringSetting(
    name: String,
    value: String,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : MutableSetting<String>(name, value, description, visibility)