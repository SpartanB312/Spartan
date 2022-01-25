package net.spartanb312.render.features.hud.information

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.graphics.impl.Render2DScope
import net.spartanb312.render.graphics.impl.drawGradientRect
import net.spartanb312.render.graphics.impl.drawString
import net.spartanb312.render.graphics.impl.legacy2D

object TestHUD : HUDModule(
    name = "TestHud",
    x = 100,
    y = 100,
    category = Category.Information,
    description = "This is description"
) {

    override fun Render2DScope.onRender() {
        legacy2D {
            drawGradientRect(
                0, 0, 100, 100,
                ColorRGB(255, 255, 0),
                ColorRGB(255, 255, 0),
                ColorRGB(0, 255, 0),
                ColorRGB(0, 255, 0),
            )
        }
        drawString("Hello world", 1, 1)
        legacy2D {
            drawGradientRect(
                200, 200, 300, 300,
                ColorRGB(255, 255, 0),
                ColorRGB(255, 255, 0),
                ColorRGB(0, 255, 0),
                ColorRGB(0, 255, 0),
            )
        }
    }

}