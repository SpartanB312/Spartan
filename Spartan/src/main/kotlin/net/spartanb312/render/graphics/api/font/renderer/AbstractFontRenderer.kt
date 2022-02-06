package net.spartanb312.render.graphics.api.font.renderer

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.spartanb312.render.core.common.delegate.AsyncCachedValue
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.common.timming.TimeUnit
import net.spartanb312.render.graphics.api.MatrixUtils
import net.spartanb312.render.graphics.api.font.RenderString
import net.spartanb312.render.graphics.api.font.Style
import net.spartanb312.render.graphics.api.font.glyph.FontGlyphs
import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.launch.Logger
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.util.*

abstract class AbstractFontRenderer(
    val font: Font,
    size: Float,
    private val textureSize: Int
) : IFontRenderer {

    abstract val renderContext: AbstractFontRenderContext

    protected open val sizeMultiplier
        get() = 1.0f

    protected open val baselineOffset
        get() = 0.0f

    protected open val charGap
        get() = 0.0f

    protected open val lineSpace
        get() = 1.0f

    protected open val lodBias
        get() = 0.0f

    protected open val shadowDist
        get() = 2.0f

    val regularGlyph = loadFont(font, size, Style.REGULAR)

    private var prevCharGap = Float.NaN
    private var prevLineSpace = Float.NaN
    private var prevShadowDist = Float.NaN

    private val renderStringMap = Object2ObjectOpenHashMap<CharSequence, RenderString>()

    private val cleanTimer = TickTimer()

    protected fun loadFont(font: Font, size: Float, style: Style): FontGlyphs {
        val fallbackFont = try {
            getFallbackFont().deriveFont(style.styleConst, size)
        } catch (e: Exception) {
            Logger.warn("Failed loading fallback font. Using Sans Serif font")
            e.printStackTrace()
            getSansSerifFont().deriveFont(style.styleConst, size)
        }
        return FontGlyphs(style.ordinal, font.deriveFont(style.styleConst, size), fallbackFont, textureSize)
    }

    override fun drawString(
        charSequence: CharSequence,
        posX: Float,
        posY: Float,
        color: ColorRGB,
        scale: Float,
        drawShadow: Boolean
    ) {
        if (cleanTimer.tickAndReset(1000L)) {
            val current = System.currentTimeMillis()
            renderStringMap.values.removeIf {
                it.tryClean(current)
            }
        }

        if (prevCharGap != charGap || prevLineSpace != lineSpace || prevShadowDist != shadowDist) {
            clearStringCache()
            prevCharGap = charGap
            prevLineSpace = lineSpace
            prevShadowDist = shadowDist
        }

        val string = charSequence.toString()
        val stringCache = renderStringMap.computeIfAbsent(string) {
            RenderString(it).build(this, charGap, lineSpace, shadowDist)
        }

        GLStateManager.texture2d(true)
        GLStateManager.blend(true)

        val modelView = MatrixUtils.loadModelViewMatrix().getMatrix()
            .translate(posX, posY, 0.0f)
            .scale(sizeMultiplier * scale, sizeMultiplier * scale, 1.0f)
            .translate(0.0f, baselineOffset, 0.0f)

        stringCache.render(modelView, color, drawShadow, lodBias)

        //Make it possible to draw series of objects without toggling prepareGL releaseGL frequently.bcs JNI consumes cpu time
        GLStateManager.texture2d(false)
        GLStateManager.useProgram(0)
    }

    //Xiaro forgot to times scale
    override fun getHeight(scale: Float): Float {
        return regularGlyph.fontHeight * scale
    }

    override fun getWidth(text: CharSequence, scale: Float): Float {
        var maxLineWidth = 0.0f
        var width = 0.0f
        val context = renderContext

        for ((index, char) in text.withIndex()) {
            if (char == '\n') {
                if (width > maxLineWidth) maxLineWidth = width
                width = 0.0f
            }
            if (context.checkFormatCode(text, index, false)) continue
            width += regularGlyph.getCharInfo(char).width + charGap
        }

        return width * sizeMultiplier * scale
    }

    override fun getWidth(char: Char, scale: Float): Float {
        return (regularGlyph.getCharInfo(char).width + charGap) * sizeMultiplier * scale
    }

    open fun destroy() {
        clearStringCache()
        regularGlyph.destroy()
    }

    fun clearStringCache() {
        renderStringMap.values.forEach {
            it.destroy()
        }
        renderStringMap.clear()
    }

    companion object {

        val availableFonts: Map<String, String> by AsyncCachedValue(5L, TimeUnit.SECONDS) {
            HashMap<String, String>().apply {
                val environment = GraphicsEnvironment.getLocalGraphicsEnvironment()

                environment.availableFontFamilyNames.forEach {
                    this[it.lowercase(Locale.ROOT)] = it
                }

                environment.allFonts.forEach {
                    val family = it.family
                    if (family != Font.DIALOG) {
                        this[it.name.lowercase(Locale.ROOT)] = family
                    }
                }
            }
        }

        fun getFallbackFont(): Font {
            return Font(fallbackFonts.firstOrNull { availableFonts.containsKey(it) }, Font.PLAIN, 64)
        }

        fun getSansSerifFont(): Font {
            return Font(Font.SANS_SERIF, Font.PLAIN, 64)
        }

        private val fallbackFonts = arrayOf(
            "microsoft yahei ui",
            "microsoft yahei",
            "noto sans jp",
            "noto sans cjk jp",
            "noto sans cjk jp",
            "noto sans cjk kr",
            "noto sans cjk sc",
            "noto sans cjk tc",
            "source han sans",
            "source han sans hc",
            "source han sans sc",
            "source han sans tc",
            "source han sans k",
        )
    }

    fun drawString(
        charSequence: CharSequence,
        posX: Float = 0.0f,
        posY: Float = 0.0f,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(charSequence, posX, posY, color, scale, false)

    fun drawStringWithShadow(
        charSequence: CharSequence,
        posX: Float = 0.0f,
        posY: Float = 0.0f,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(charSequence, posX, posY, color, scale, true)

    fun drawCenteredString(
        charSequence: CharSequence,
        posX: Float = 0.0f,
        posY: Float = 0.0f,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(
        charSequence,
        posX - getWidth(charSequence.toString(), scale) / 2f,
        posY - getHeight(scale) / 2f,
        color,
        scale,
        false
    )

    fun drawCenteredStringWithShadow(
        charSequence: CharSequence,
        posX: Float = 0.0f,
        posY: Float = 0.0f,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(
        charSequence,
        posX - getWidth(charSequence.toString(), scale) / 2f,
        posY - getHeight(scale) / 2f,
        color,
        scale,
        true
    )

    fun drawString(
        charSequence: CharSequence,
        posX: Double = 0.0,
        posY: Double = 0.0,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(charSequence, posX.toFloat(), posY.toFloat(), color, scale, false)

    fun drawStringWithShadow(
        charSequence: CharSequence,
        posX: Double = 0.0,
        posY: Double = 0.0,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(charSequence, posX.toFloat(), posY.toFloat(), color, scale, true)

    fun drawCenteredString(
        charSequence: CharSequence,
        posX: Double = 0.0,
        posY: Double = 0.0,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(
        charSequence,
        posX.toFloat() - getWidth(charSequence.toString(), scale) / 2f,
        posY.toFloat() - getHeight(scale) / 2f,
        color,
        scale,
        false
    )

    fun drawCenteredStringWithShadow(
        charSequence: CharSequence,
        posX: Double = 0.0,
        posY: Double = 0.0,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(
        charSequence,
        posX.toFloat() - getWidth(charSequence.toString(), scale) / 2f,
        posY.toFloat() - getHeight(scale) / 2f,
        color,
        scale,
        true
    )

    fun drawString(
        charSequence: CharSequence,
        posX: Int = 0,
        posY: Int = 0,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0F,
    ) = drawString(charSequence, posX.toFloat(), posY.toFloat(), color, scale, false)

    fun drawStringWithShadow(
        charSequence: CharSequence,
        posX: Int = 0,
        posY: Int = 0,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0F,
    ) = drawString(charSequence, posX.toFloat(), posY.toFloat(), color, scale, true)

    fun drawCenteredString(
        charSequence: CharSequence,
        posX: Int = 0,
        posY: Int = 0,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(
        charSequence,
        posX.toFloat() - getWidth(charSequence.toString(), scale) / 2f,
        posY.toFloat() - getHeight(scale) / 2f,
        color,
        scale,
        false
    )

    fun drawCenteredStringWithShadow(
        charSequence: CharSequence,
        posX: Int = 0,
        posY: Int = 0,
        color: ColorRGB = ColorRGB(255, 255, 255),
        scale: Float = 1.0f,
    ) = drawString(
        charSequence,
        posX.toFloat() - getWidth(charSequence.toString(), scale) / 2f,
        posY.toFloat() - getHeight(scale) / 2f,
        color,
        scale,
        true
    )


}