package net.spartanb312.render.features.hud

import net.spartanb312.render.core.setting.invisible
import net.spartanb312.render.features.common.AbstractModule
import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.common.Render2DObject
import org.lwjgl.input.Keyboard

@Suppress("LeakingThis")
abstract class HUDModule(
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
) : AbstractModule(name, alias, category, description, priority, keyBind, visibility), Render2DObject {

    override var renderPriority by setting("Layer", layer).invisible()

    /**
     * Position values delegate
     */
    var x by setting("HUD_X", x).invisible()
    var y by setting("HUD_Y", y).invisible()
    var width by setting("HUD_WIDTH", width).invisible()
    var height by setting("HUD_HEIGHT", height).invisible()

    fun resize(width: Int, height: Int) {
        this.width = width
        this.height = height
    }

    fun moveTo(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun layer(layer: Int) {
        this.renderPriority = layer
    }

}