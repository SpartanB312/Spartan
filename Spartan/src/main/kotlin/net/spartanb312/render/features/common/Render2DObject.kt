package net.spartanb312.render.features.common

import net.spartanb312.render.features.SpartanCore.subscribe
import net.spartanb312.render.features.manager.Render2DManager
import net.spartanb312.render.graphics.impl.Render2DScope

interface Render2DObject : ToggleableFeature {
    var renderPriority: Int
    fun Render2DScope.onRender()
}

fun Render2DObject.subscribeToRender2DManager() {
    Render2DManager.subscribe()
}