package net.spartanb312.render.features.manager

import net.minecraft.client.gui.ScaledResolution
import net.spartanb312.render.core.common.delegate.AsyncUpdateValue
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.event.client.AsyncTickEvent
import net.spartanb312.render.util.mc
import org.lwjgl.input.Mouse

object ResolutionHelper {

    @JvmStatic
    var resolution by AsyncUpdateValue {
        ScaledResolution(mc)
    }.also { r ->
        listener<AsyncTickEvent> {
            if (mc.player == null || mc.world == null) {
                r.update()
            }
        }
    }

    val scaledWidth: Int get() = resolution.scaledWidth
    val scaledHeight: Int get() = resolution.scaledHeight

    val scaledWidthD: Double get() = resolution.scaledWidth_double
    val scaledHeightD: Double get() = resolution.scaledHeight_double

    val mouseX: Int get() = Mouse.getX() * resolution.scaledWidth / mc.displayWidth
    val mouseY: Int get() = resolution.scaledHeight - Mouse.getY() * resolution.scaledHeight / mc.displayHeight - 1

}