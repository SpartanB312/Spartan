package net.spartanb312.render.features.ui.structure

import net.spartanb312.render.graphics.impl.Render2DScope
import kotlin.math.max
import kotlin.math.min

interface Component {

    var x: Double
    var y: Double
    var width: Double
    var height: Double

    fun Render2DScope.onRender() {
    }

    fun onKeyTyped(typedChar: Char, keyCode: Int) {
    }

    fun onKeyInput() {
    }

    fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Boolean = false

    fun onMouseReleased(mouseX: Int, mouseY: Int, state: Int) {
    }

    fun onMouseMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long) {
    }

    fun onMouseInput() {
    }

    fun isHoovered(mouseX: Int, mouseY: Int): Boolean {
        return mouseX >= min(x, x + width) && mouseX <= max(x, x + width)
                && mouseY >= min(y, y + height) && mouseY <= max(y, y + height)
    }

    fun isVisible(): Boolean = true

}