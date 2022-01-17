package net.spartanb312.render.core.setting.impl.other

import net.spartanb312.render.core.setting.MutableSetting

class ActionButton(
    name: String,
    value: () -> Unit,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : MutableSetting<() -> Unit>(name, value, description, visibility)