package net.spartanb312.render.features.ui

import net.spartanb312.render.features.ui.wrapper.ScreenRenderer
import net.spartanb312.render.features.ui.wrapper.WrappedScreen
import net.spartanb312.render.util.mc

object DisplayManager {

    fun displayRenderer(screenRenderer: ScreenRenderer) {
        mc.displayGuiScreen(WrappedScreen(screenRenderer))
    }

}