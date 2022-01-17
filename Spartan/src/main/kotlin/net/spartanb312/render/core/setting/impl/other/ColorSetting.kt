package net.spartanb312.render.core.setting.impl.other

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.setting.MutableSetting

class ColorSetting(
    name: String,
    value: ColorRGB,
    description: String = "",
    visibility: (() -> Boolean) = { true }
) : MutableSetting<ColorRGB>(name, value, description, visibility)