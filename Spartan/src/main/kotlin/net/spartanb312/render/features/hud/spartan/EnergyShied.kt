package net.spartanb312.render.features.hud.spartan

import net.spartanb312.render.core.setting.at
import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.graphics.impl.Render2DScope

object EnergyShied : HUDModule(
    name = "Energy Shield",
    alias = arrayOf("Shield", "Spartan"),
    x = 300,
    y = 300,
    category = Category.SpartanHUD,
    description = "Spartans Never die"
) {

    private var mode by setting("Mode", Mode.Halo3, "The style of the energy shield")
    private var autoScale by setting("AutoScale", true, "Automatically calc scale")
    private var scale by setting("Scale", 1.0f, 0.0f..5.0f, 0.1f, "Specify the scale").at { !autoScale }

    override fun Render2DScope.onRender() {

    }

    private fun drawHalo3Energy() {

    }

    private fun drawReach() {

    }

    private fun drawInfinite() {

    }

    enum class Mode { Reach, Halo3, Infinite }

}