package net.spartanb312.render.graphics.api.font

import net.spartanb312.render.graphics.api.font.renderer.AbstractFontRenderer
import net.spartanb312.render.graphics.api.font.renderer.ExtendedFontRenderer
import net.spartanb312.render.graphics.api.font.setting.FontSetting
import net.spartanb312.render.graphics.api.font.setting.LinkedSettableFontRenderer
import net.spartanb312.render.launch.Logger
import net.spartanb312.render.launch.ResourceCenter
import java.awt.Font
import java.io.InputStream

fun InputStream.createFont(type: Int): Font = Font.createFont(Font.TRUETYPE_FONT, this)

fun createFont(type: Int, path: String): Font = Font.createFont(type, ResourceCenter.getResourceAsStream(path)!!)

fun createFontRenderer(
    name: String,
    size: Float,
    textureSize: Int,
    type: Int,
    pathGroup: String = "assets/spartan/font/"
): ExtendedFontRenderer {
    val font = try {
        createFont(type, pathGroup + name)
    } catch (e: Exception) {
        Logger.fatal("Failed to load font $name.Using Sans Serif font.")
        e.printStackTrace()
        AbstractFontRenderer.getSansSerifFont()
    }
    return ExtendedFontRenderer(font, size, textureSize)
}

fun createFontRenderer(
    inputStream: InputStream,
    size: Float,
    textureSize: Int,
    type: Int,
): ExtendedFontRenderer {
    val font = try {
        inputStream.createFont(type)
    } catch (e: Exception) {
        Logger.fatal("Failed to load font from input stream.Using Sans Serif font.")
        e.printStackTrace()
        AbstractFontRenderer.getSansSerifFont()
    }
    return ExtendedFontRenderer(font, size, textureSize)
}

fun Font.linkTo(setting: FontSetting, size: Float = 64f, textureSize: Int = 2048): LinkedSettableFontRenderer =
    LinkedSettableFontRenderer(this, setting, size, textureSize)
