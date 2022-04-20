package net.spartanb312.render.features.manager

import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.graphics.impl.item.other.IconDisplay
import net.spartanb312.render.graphics.api.texture.DrawTexture
import net.spartanb312.render.graphics.api.texture.MipmapTexture
import net.spartanb312.render.graphics.impl.GLStateManager
import org.lwjgl.opengl.GL11

object TextureManager {

    object PlayerIDFrame {
        //Icon-pic
        val CCCP = IconDisplay.from("assets/spartan/image/id/icon/CCCP.png")!!
        val INTERNET_EXPLORER = IconDisplay.from("assets/spartan/image/id/icon/InternetExplorer.png")!!
        val NOBLE = IconDisplay.from("assets/spartan/image/id/icon/Noble.png")!!
        val SPARTAN_B312 = IconDisplay.from("assets/spartan/image/id/icon/Spartan_B312.png")!!
        val SPARTAN_PALMER = IconDisplay.from("assets/spartan/image/id/icon/Spartan_Palmer.png")!!

        //Icon-number
        val Num_1 = IconDisplay.word("1", FontManager.hollowFont)
        val Num_2 = IconDisplay.word("2", FontManager.hollowFont)
        val Num_3 = IconDisplay.word("3", FontManager.hollowFont)
        val Num_4 = IconDisplay.word("4", FontManager.hollowFont)
        val Num_5 = IconDisplay.word("5", FontManager.hollowFont)
        val Num_6 = IconDisplay.word("6", FontManager.hollowFont)
        val Num_7 = IconDisplay.word("7", FontManager.hollowFont)
        val Num_8 = IconDisplay.word("8", FontManager.hollowFont)
        val Num_9 = IconDisplay.word("9", FontManager.hollowFont)
        val Num_0 = IconDisplay.word("0", FontManager.hollowFont)

        //Base
        val BLUE_ARROW = MipmapTexture.from("assets/spartan/image/id/base/BlueArrow.png")!!
        val BLUE_CIRCLE = MipmapTexture.from("assets/spartan/image/id/base/BlueCircle.png")!!
        val BLUE_PULSE = MipmapTexture.from("assets/spartan/image/id/base/BluePulse.png")!!
        val V = MipmapTexture.from("assets/spartan/image/id/base/V.png")!!

        //Main-pic
        val CARBON_FIBRE = DrawTexture.pic("assets/spartan/image/id/main/CarbonFibre.jpg")!!
        val FRACTURE = DrawTexture.pic("assets/spartan/image/id/main/Fracture.png")!!
        val MIA = DrawTexture.pic("assets/spartan/image/id/main/MIA.jpg")!!
        val SACRIFICE = DrawTexture.pic("assets/spartan/image/id/main/Sacrifice.png")!!
        val STELLAR = DrawTexture.pic("assets/spartan/image/id/main/Stellar.png")!!

        //Main-shader
        val SPACE = DrawTexture.shader("assets/spartan/shader/menu/Space.fsh")!!
    }

    fun MipmapTexture.drawTexture(
        startX: Number,
        startY: Number,
        endX: Number,
        endY: Number,
        color: ColorRGB = ColorRGB.WHITE,
    ): MipmapTexture {
        GLStateManager.useProgram(0)
        color.glColor()
        GLStateManager.texture2d(true)
        GLStateManager.alpha(true)
        GLStateManager.blend(true)
        bindTexture()
        val width = endX.toDouble() - startX.toDouble()
        val height = endY.toDouble() - startY.toDouble()
        drawScaledCustomSizeModalRect(
            startX.toDouble(), startY.toDouble(), 0.0,
            0.0,
            width,
            height,
            width,
            height,
            width,
            height
        )
        unbindTexture()
        GLStateManager.texture2d(false)
        GLStateManager.resetColor()
        return this
    }

    fun drawScaledCustomSizeModalRect(
        x: Double,
        y: Double,
        u: Double,
        v: Double,
        uWidth: Double,
        vHeight: Double,
        width: Double,
        height: Double,
        tileWidth: Double,
        tileHeight: Double,
    ) {
        val f = 1.0f / tileWidth
        val f1 = 1.0f / tileHeight

        val tessellator = Tessellator.getInstance()
        val bufferbuilder = tessellator.buffer
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX)
        bufferbuilder.pos(x, y + height, 0.0)
            .tex(u * f, (v + vHeight) * f1).endVertex()
        bufferbuilder.pos((x + width), (y + height), 0.0)
            .tex((u + uWidth) * f, (v + vHeight) * f1).endVertex()
        bufferbuilder.pos((x + width), y, 0.0)
            .tex((u + uWidth) * f, v * f1).endVertex()
        bufferbuilder.pos(x, y, 0.0)
            .tex(u * f, v * f1).endVertex()
        tessellator.draw()
    }

}