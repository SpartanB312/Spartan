package net.spartanb312.render.features.ui

import net.spartanb312.render.features.ui.DisplayManager.Renderers.MAIN_MENU
import net.spartanb312.render.features.ui.impl.menu.MainMenuRenderer
import net.spartanb312.render.features.ui.impl.menu.MenuGateRenderer
import net.spartanb312.render.features.ui.wrapper.ScreenRenderer
import net.spartanb312.render.features.ui.wrapper.WrappedScreen
import net.spartanb312.render.util.mc

object DisplayManager {

    //Cache renderer here to make all the default renderers could be loaded
    object Renderers {
        @JvmField
        val MENU_GATE = MenuGateRenderer
        @JvmField
        val MAIN_MENU = MainMenuRenderer
    }

    //Delegate screen renderer
    val screen by WrappedScreen(MAIN_MENU)

    fun ScreenRenderer.displayRenderer() = screen.setAndUse(this)
    val ScreenRenderer.isDisplaying get() = mc.currentScreen == screen && screen.renderer == this


}