package net.spartanb312.render.graphics.impl

import net.spartanb312.render.core.common.delegate.AsyncUpdateValue
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.event.client.GameLoopEvent
import net.spartanb312.render.features.manager.FontManager.getFontRendererByName

object FontRenderer {

    private var shouldUpdate = false
    var currentFontName = "Microsoft YaHei UI"
        set(value) {
            if (field != value) {
                shouldUpdate = true
                field = value
            }
        }

    //delegate renderer
    var renderer by AsyncUpdateValue {
        currentFontName.getFontRendererByName()
    }.also { r ->
        listener<GameLoopEvent.Pre>(Int.MAX_VALUE, true) {
            if (shouldUpdate) r.update()
        }
    }

    fun drawString(
        charSequence: CharSequence,
        posX: Float = 0.0f,
        posY: Float = 0.0f,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = renderer.drawString(charSequence, posX, posY, color, scale, false)

    fun drawStringWithShadow(
        charSequence: CharSequence,
        posX: Float = 0.0f,
        posY: Float = 0.0f,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = renderer.drawString(charSequence, posX, posY, color, scale, true)

    fun drawStringInJava(
        string: String,
        posX: Float,
        posY: Float,
        color: Int,
        scale: Float,
    ) = renderer.drawStringJava(string, posX, posY, color, scale, false)

    fun drawStringWithShadowInJava(
        string: String,
        posX: Float,
        posY: Float,
        color: Int,
        scale: Float,
    ) = renderer.drawStringJava(string, posX, posY, color, scale, true)

    fun getHeight(scale: Float): Float = renderer.getHeight(scale)

    fun getWidth(text: CharSequence, scale: Float): Float = renderer.getWidth(text, scale)

}