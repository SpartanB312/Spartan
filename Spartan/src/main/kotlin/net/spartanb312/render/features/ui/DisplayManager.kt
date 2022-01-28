package net.spartanb312.render.features.ui

import net.spartanb312.render.features.ui.impl.menu.MainMenuRenderer
import net.spartanb312.render.features.ui.wrapper.ScreenRenderer
import net.spartanb312.render.features.ui.wrapper.WrappedScreen
import net.spartanb312.render.util.mc

object DisplayManager {

    private val screen by WrappedScreen(MainMenuRenderer)

    fun ScreenRenderer.displayRenderer() = screen.setAndUse(this)
    val ScreenRenderer.isDisplaying get() = mc.currentScreen == screen && screen.renderer == this


}