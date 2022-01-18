package net.spartanb312.render.features.hud.information

import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.hud.HUDModule
import net.spartanb312.render.graphics.impl.Render2DScope

object TestHUD : HUDModule(
    name = "TestHud",
    category = Category.Information,
    description = "This is description"
) {

    override fun Render2DScope.onRender() {

    }

}