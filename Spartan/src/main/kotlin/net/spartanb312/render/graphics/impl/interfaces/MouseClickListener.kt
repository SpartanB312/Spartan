package net.spartanb312.render.graphics.impl.interfaces

interface MouseClickListener {
    fun onMouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Boolean = false
}