package net.spartanb312.render.features.manager

import net.spartanb312.render.graphics.api.font.createFont
import net.spartanb312.render.graphics.api.font.createFontRenderer
import net.spartanb312.render.graphics.api.font.linkTo
import net.spartanb312.render.graphics.api.font.renderer.AbstractFontRenderer
import net.spartanb312.render.graphics.api.font.setting.FontSetting
import net.spartanb312.render.graphics.api.font.setting.LinkedSettableFontRenderer
import net.spartanb312.render.launch.Logger
import java.awt.Font
import java.io.InputStream

object FontManager {

    val mainFontSetting = FontSetting()
    private val cachedFontRenderers = mutableMapOf<String, LinkedSettableFontRenderer>()

    val mainFont = MainResourceManager.getSpartanResourceStream("font/Microsoft YaHei UI.ttc")!!
        .generateFontRenderer(
            fontName = "MainFont",
            fontSetting = mainFontSetting,
            type = Font.TRUETYPE_FONT,
            size = 64F,
            textureSize = 8192,
        ).also { cachedFontRenderers["Microsoft YaHei UI"] = it }

    val haloFont = createFontRenderer("Halo.ttf", 128F, 8192)
    val infoFont = createFontRenderer("Microsoft YaHei UI.ttc", 64F, 2048)
    val hollowFont = createFontRenderer("Hollow.ttf", 128F, 4096)

    @JvmStatic
    fun String.getFontRendererByName(): LinkedSettableFontRenderer =
        cachedFontRenderers.getOrDefault(this, mainFont)

    @JvmStatic
    fun InputStream.generateFontRenderer(
        fontName: String,
        fontSetting: FontSetting,
        type: Int,
        size: Float,
        textureSize: Int,
    ): LinkedSettableFontRenderer = try {
        this.createFont(type)
    } catch (e: Exception) {
        Logger.fatal("Failed to load font $fontName.Using Sans Serif font.")
        e.printStackTrace()
        AbstractFontRenderer.getSansSerifFont()
    }.linkTo(fontSetting, size, textureSize).also {
        cachedFontRenderers[fontName] = it
    }

    @JvmStatic
    fun String.generateFontRenderer(
        fontName: String,
        fontSetting: FontSetting,
        type: Int,
        size: Float,
        textureSize: Int,
    ): LinkedSettableFontRenderer = try {
        createFont(type, this)
    } catch (e: Exception) {
        Logger.fatal("Failed to load font $fontName.Using Sans Serif font.")
        e.printStackTrace()
        AbstractFontRenderer.getSansSerifFont()
    }.linkTo(fontSetting, size, textureSize).also {
        cachedFontRenderers[fontName] = it
    }

}