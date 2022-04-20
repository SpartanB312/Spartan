package net.spartanb312.render.features.ui

import net.spartanb312.render.features.ui.DisplayManager.Renderers.MAIN_MENU
import net.spartanb312.render.features.ui.menu.MainMenuRenderer
import net.spartanb312.render.features.ui.menu.MenuGateRenderer
import net.spartanb312.render.features.ui.wrapper.DelegateRenderer
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
    val screen by WrappedScreen(DelegateRenderer(MAIN_MENU))

    fun DelegateRenderer.displayRenderer() = screen.setAndUse(this)
    fun ScreenRenderer.displayRenderer() = screen.setAndUse(DelegateRenderer(MAIN_MENU))
    val ScreenRenderer.isDisplaying get() = mc.currentScreen == screen && screen.delegate.renderer == this


}