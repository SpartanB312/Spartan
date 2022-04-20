package net.spartanb312.render.graphics.impl.item.other

import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.features.manager.TextureManager.drawTexture
import net.spartanb312.render.graphics.api.font.renderer.ExtendedFontRenderer
import net.spartanb312.render.graphics.api.texture.MipmapTexture
import kotlin.math.min

abstract class IconDisplay(
    var baseColor: ColorRGB = ColorRGB.WHITE,
    var mainColor: ColorRGB = ColorRGB.WHITE,
) {

    abstract fun display(
        startX: Number,
        startY: Number,
        endX: Number,
        endY: Number,
        baseColor: ColorRGB = this.baseColor,
        mainColor: ColorRGB = this.mainColor,
    )

    class TextureIcon(
        var base: MipmapTexture? = null,
        var main: MipmapTexture,
        baseColor: ColorRGB = ColorRGB.WHITE,
        mainColor: ColorRGB = ColorRGB.WHITE,
    ) : IconDisplay(baseColor, mainColor) {
        override fun display(
            startX: Number,
            startY: Number,
            endX: Number,
            endY: Number,
            baseColor: ColorRGB,
            mainColor: ColorRGB,
        ) {
            this.baseColor = baseColor
            this.mainColor = mainColor
            base?.drawTexture(startX, startY, endX, endY, baseColor)
            main.drawTexture(startX, startY, endX, endY, mainColor)
        }
    }

    class WordIcon(
        var base: MipmapTexture? = null,
        var word: String,
        var shadow: Boolean = true,
        val fontRenderer: ExtendedFontRenderer,
        baseColor: ColorRGB = ColorRGB.WHITE,
        mainColor: ColorRGB = ColorRGB.WHITE,
    ) : IconDisplay(baseColor, mainColor) {
        override fun display(
            startX: Number,
            startY: Number,
            endX: Number,
            endY: Number,
            baseColor: ColorRGB,
            mainColor: ColorRGB,
        ) {
            this.baseColor = baseColor
            this.mainColor = mainColor
            base?.drawTexture(startX, startY, endX, endY, baseColor)
            val width = (endX.toDouble() - startX.toDouble())
            val height = (endY.toDouble() - startY.toDouble())
            val scale = min(width, height).toFloat() / 80f * 0.6f
            if (shadow) fontRenderer.drawCenteredStringWithShadow(
                word,
                startX.toDouble() + width / 2.0,
                startY.toDouble() + height / 2.0,
                mainColor,
                scale
            ) else fontRenderer.drawCenteredString(
                word,
                startX.toDouble() + width / 2.0,
                startY.toDouble() + height / 2.0,
                mainColor,
                scale
            )
        }
    }

    companion object {

        fun word(
            word: String,
            fontRenderer: ExtendedFontRenderer,
            mainColor: ColorRGB = ColorRGB.WHITE,
            shadow: Boolean = true,
        ): IconDisplay = WordIcon(word = word, mainColor = mainColor, fontRenderer = fontRenderer, shadow = shadow)

        fun word(
            word: String,
            fontRenderer: ExtendedFontRenderer,
            basePath: String,
            baseColor: ColorRGB = ColorRGB.WHITE,
            mainColor: ColorRGB = ColorRGB.WHITE,
            shadow: Boolean = true,
        ): IconDisplay? {
            val basePic = MipmapTexture.from(basePath) ?: return null
            return WordIcon(
                base = basePic,
                word = word,
                baseColor = baseColor,
                mainColor = mainColor,
                fontRenderer = fontRenderer,
                shadow = shadow
            )
        }

        fun from(path: String, mainColor: ColorRGB = ColorRGB.WHITE): IconDisplay? {
            val mainPic = MipmapTexture.from(path) ?: return null
            return TextureIcon(main = mainPic, mainColor = mainColor)
        }

        fun from(
            basePath: String,
            mainPath: String,
            baseColor: ColorRGB = ColorRGB.WHITE,
            mainColor: ColorRGB = ColorRGB.WHITE,
        ): IconDisplay? {
            val basePic = MipmapTexture.from(basePath) ?: return null
            val mainPic = MipmapTexture.from(mainPath) ?: return null
            return TextureIcon(basePic, mainPic, baseColor, mainColor)
        }

    }

}