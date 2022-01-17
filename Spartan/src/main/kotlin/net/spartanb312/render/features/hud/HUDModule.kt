package net.spartanb312.render.features.hud

import net.spartanb312.render.core.setting.invisible
import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.common.Category
import org.lwjgl.input.Keyboard

@Suppress("LeakingThis")
open class HUDModule(
    name: String,
    alias: Array<String> = emptyArray(),
    category: Category,
    description: String,
    x: Int = 0,
    y: Int = 0,
    height: Int = 0,
    width: Int = 0,
    priority: Int = 1000,
    layer: Int = 0,
    visibility: Boolean = false,
    keyBind: Int = Keyboard.KEY_NONE
) : AbstractModule(name, alias, category, description, priority, keyBind, visibility) {

    /**
     * Position values
     */
    private val x0 = setting("HUD_X", x).invisible()
    private val y0 = setting("HUD_Y", y).invisible()
    private val width0 = setting("HUD_WIDTH", width).invisible()
    private val height0 = setting("HUD_HEIGHT", height).invisible()
    private val layer0 = setting("Layer", layer).invisible()

    /**
     * Delegate getter of position values
     */
    val x by x0
    val y by y0
    val width by width0
    val height by height0
    val layer by layer0

    fun resize(width: Int, height: Int) {
        width0.value = width
        height0.value = height
    }

    fun moveTo(x: Int, y: Int) {
        x0.value = x
        y0.value = y
    }

    fun layer(layer: Int) {
        layer0.value = layer
    }

}