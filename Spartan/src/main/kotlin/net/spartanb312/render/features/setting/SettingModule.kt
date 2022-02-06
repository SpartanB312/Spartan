package net.spartanb312.render.features.setting

import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.common.Category
import org.lwjgl.input.Keyboard

open class SettingModule(
    name: String,
    alias: Array<String> = emptyArray(),
    category: Category,
    description: String
) : AbstractModule(name, alias, category, description, Int.MAX_VALUE, Keyboard.KEY_NONE, false)