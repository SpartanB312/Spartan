package net.spartanb312.render.graphics.impl.interfaces

interface MouseMoveListener {
    fun onMouseMove(mouseX: Int, mouseY: Int, clickedMouseButton: Int, timeSinceLastClick: Long)
}