package net.spartanb312.render.features.utility

import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.common.Category
import org.lwjgl.input.Keyboard

/**
 * It's a kind of cheat.I think.
 */
open class UtilityModule(
    name: String,
    alias: Array<String> = emptyArray(),
    category: Category,
    description: String,
    priority: Int = 1000,
    visibility: Boolean = true,
    keyBind: Int = Keyboard.KEY_NONE
) : AbstractModule(name, alias, category, description, priority, keyBind, visibility)