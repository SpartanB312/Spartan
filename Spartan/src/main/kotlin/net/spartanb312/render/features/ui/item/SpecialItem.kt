package net.spartanb312.render.features.ui.item

/**
 * Those who need to be reset
 */
interface SpecialItem {
    fun reset()
    fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Boolean = false
}