package net.spartanb312.render.core.setting.impl.other

import net.spartanb312.render.core.common.KeyBind

class BindSetting(
    name: String,
    value: KeyBind,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : ExtendSetting<KeyBind>(name, value, description, visibility)