package net.spartanb312.render.features.manager

import net.minecraft.client.gui.ScaledResolution
import net.spartanb312.render.core.common.delegate.AsyncUpdateValue
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.event.client.AsyncTickEvent
import net.spartanb312.render.util.mc

object ResolutionHelper {

    private val resolution0 = AsyncUpdateValue {
        ScaledResolution(mc)
    }.also { r ->
        listener<AsyncTickEvent> {
            if (mc.player == null || mc.world == null) {
                r.update()
            }
        }
    }

    val resolution by resolution0

    @JvmStatic
    fun update(resolution: ScaledResolution? = null) = resolution0.update(resolution)

}