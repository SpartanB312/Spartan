package net.spartanb312.render.core.setting.impl.primitive

import net.spartanb312.render.core.setting.MutableSetting

open class BooleanSetting(
    name: String,
    value: Boolean,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : MutableSetting<Boolean>(name, value, description, visibility)