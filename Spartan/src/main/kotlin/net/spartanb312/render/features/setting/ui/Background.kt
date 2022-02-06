package net.spartanb312.render.features.setting.ui

import net.minecraft.client.gui.GuiScreen
import net.spartanb312.render.Spartan.DEFAULT_FILE_GROUP
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.common.timming.passedAndRun
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.core.graphics.ConvergeUtil.converge
import net.spartanb312.render.core.io.JPG_SUFFIX
import net.spartanb312.render.core.io.PNG_SUFFIX
import net.spartanb312.render.core.io.readFiles
import net.spartanb312.render.core.setting.at
import net.spartanb312.render.core.setting.des
import net.spartanb312.render.features.SpartanCore
import net.spartanb312.render.features.common.Category
import net.spartanb312.render.features.common.GLContextRequired
import net.spartanb312.render.features.event.client.TickEvent
import net.spartanb312.render.features.manager.MainResourceManager
import net.spartanb312.render.features.manager.ResolutionHelper
import net.spartanb312.render.features.setting.SettingModule
import net.spartanb312.render.features.ui.DisplayManager
import net.spartanb312.render.graphics.api.shader.GLSLSandbox
import net.spartanb312.render.graphics.api.texture.MipmapTexture
import net.spartanb312.render.graphics.api.texture.useTexture
import net.spartanb312.render.graphics.impl.GLStateManager
import net.spartanb312.render.graphics.impl.drawRect
import net.spartanb312.render.graphics.impl.legacy.Legacy2DRenderer
import net.spartanb312.render.graphics.impl.matrix
import net.spartanb312.render.graphics.impl.translate
import net.spartanb312.render.launch.Logger
import net.spartanb312.render.util.mc
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO

@GLContextRequired
object Background : SettingModule(
    name = "Background",
    alias = arrayOf("bg"),
    category = Category.UISetting,
    description = "The background of UI"
) {

    private const val PICTURE_PATH = DEFAULT_FILE_GROUP + "background/"

    /**
     * General
     */
    private var renderMode by setting("RenderMode", RenderMode.Shader)
        .des("The render mode of background")
    var fpsLimit by setting("FPS Limit", 60, 0..300, 5)
        .des("Limit the fps")

    /**
     * Picture
     */
    private val pictureMode by setting("PlayMode", PictureMode.Slide)
        .des("The way to display background pictures")
        .at { renderMode == RenderMode.Picture }
    private val slideUpdateTime by setting("SlideUpdateTime", 3, 0..60, 1)
        .des("Update Time unit: second")
        .at { renderMode == RenderMode.Picture && pictureMode == PictureMode.Slide }
    private val reloadPicture by setting("ReloadPicture", { reloadPicture() })
        .des("Click here to reload picture.Picture(jpg or png) at .minecraft/Spartan/background/")
        .at { renderMode == RenderMode.Picture }

    /**
     * Shader
     */
    private val shaderMode by setting("ShaderMode", ShaderMode.Random)
        .des("The way to display background shader")
        .at { renderMode == RenderMode.Shader }
    private val shader by setting("Shader", Shaders.GreenNebula)
        .des("Specify a shader")
        .at { renderMode == RenderMode.Shader && shaderMode == ShaderMode.Specified }
        .listen { SpartanCore.onMain { resetBackground() } }

    @Volatile
    private var loadingPicture = false
    private val defTexture = MipmapTexture(
        ImageIO.read(MainResourceManager.getSpartanResourceStream("image/background.png")!!),
        GL11.GL_RGBA,
        3
    )

    private val loadedTextures = mutableListOf<MipmapTexture>()
    private var loadedTexture = defTexture
    private var currentTexture = defTexture
    private var nextTexture: MipmapTexture? = null
    private var waitedTexture: MipmapTexture? = null
    private var alpha = 0F
        set(value) {
            field = value
            val cacheWaitedTexture = waitedTexture
            if (field == 0F && cacheWaitedTexture != null) {
                waitedTexture = null
                nextTexture = cacheWaitedTexture
                field = 255F
            }
        }

    /**
     * Picture slides
     */
    private val pictureUpdateTimer = TickTimer()
    private var nextIndex = 0

    init {
        reloadPicture()
        listener<TickEvent.Async.Pre>(true) {
            pictureUpdateTimer.passedAndRun(slideUpdateTime * 1000) {
                if (pictureMode == PictureMode.Slide) {
                    val cachedList = loadedTextures.toList()
                    if (cachedList.isNotEmpty()) {
                        if (nextIndex <= cachedList.size - 1) {
                            cachedList[nextIndex].setImage()
                            nextIndex++
                        } else {
                            cachedList[0].setImage()
                            nextIndex = 1
                        }
                    }
                } else {
                    nextTexture = null
                    waitedTexture = null
                    currentTexture = loadedTexture
                }
            }
        }
    }

    private fun reloadPicture() {
        if (!loadingPicture) {
            loadingPicture = true
            val tempPictures = mutableListOf<MipmapTexture>()
            fun File.solve() {
                try {
                    MipmapTexture(ImageIO.read(FileInputStream(this)), GL11.GL_RGBA, 3).also {
                        tempPictures.add(it)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            PICTURE_PATH.readFiles(JPG_SUFFIX) { it.solve() }
            PICTURE_PATH.readFiles(PNG_SUFFIX) { it.solve() }
            loadedTextures.clear()
            loadedTextures.addAll(tempPictures)
            Logger.info("Loaded ${loadedTextures.size} background pictures")
            loadedTexture = loadedTextures.getOrNull(0) ?: defTexture
            loadingPicture = false
        }
    }

    private val shaderCache = HashMap<String, GLSLSandbox>()
    private var initTime = System.currentTimeMillis()
    private var currentShader = getShader()

    private var currentX = 0f
    private var currentY = 0f

    private val alphaTimer = TickTimer()

    fun drawBackground() {
        GLStateManager.texture2d(true)
        GLStateManager.blend(true)
        when (renderMode) {
            RenderMode.Shader -> renderShader()
            else -> renderImage()
        }
        GLStateManager.texture2d(false)
    }

    fun MipmapTexture.setImage() {
        waitedTexture = this
    }

    private fun renderImage() {
        alphaTimer.passedAndRun(20) {
            alpha = alpha.converge(0F, 0.1F)
        }
        val sr = ResolutionHelper.resolution
        val cachedNextTexture = nextTexture
        val cachedCurrentTexture = currentTexture
        if (cachedNextTexture != null && pictureMode == PictureMode.Slide) {
            drawImage(
                ResolutionHelper.mouseX,
                ResolutionHelper.mouseY
            ) {
                GL11.glColor4f(1F, 1F, 1F, 1F)
                cachedNextTexture.useTexture {
                    GuiScreen.drawScaledCustomSizeModalRect(
                        -10,
                        -10,
                        0f,
                        0f,
                        sr.scaledWidth + 20,
                        sr.scaledHeight + 20,
                        sr.scaledWidth + 20,
                        sr.scaledHeight + 20,
                        (sr.scaledWidth + 20).toFloat(),
                        (sr.scaledHeight + 20).toFloat()
                    )
                }
                GL11.glColor4f(1F, 1F, 1F, 1F)
            }
            drawImage(
                ResolutionHelper.mouseX,
                ResolutionHelper.mouseY
            ) {
                GL11.glColor4f(1F, 1F, 1F, alpha.toInt().coerceIn(0..255) / 255F)
                cachedCurrentTexture.useTexture {
                    GuiScreen.drawScaledCustomSizeModalRect(
                        -10,
                        -10,
                        0f,
                        0f,
                        sr.scaledWidth + 20,
                        sr.scaledHeight + 20,
                        sr.scaledWidth + 20,
                        sr.scaledHeight + 20,
                        (sr.scaledWidth + 20).toFloat(),
                        (sr.scaledHeight + 20).toFloat()
                    )
                }
                GL11.glColor4f(1F, 1F, 1F, 1F)
            }
            if (alpha == 0F) {
                currentTexture = cachedNextTexture
                nextTexture = null
            }
        } else drawImage(
            ResolutionHelper.mouseX,
            ResolutionHelper.mouseY
        ) {
            GL11.glColor4f(1F, 1F, 1F, 1F)
            cachedCurrentTexture.useTexture {
                GuiScreen.drawScaledCustomSizeModalRect(
                    -10,
                    -10,
                    0f,
                    0f,
                    sr.scaledWidth + 20,
                    sr.scaledHeight + 20,
                    sr.scaledWidth + 20,
                    sr.scaledHeight + 20,
                    (sr.scaledWidth + 20).toFloat(),
                    (sr.scaledHeight + 20).toFloat()
                )
            }
            GL11.glColor4f(1F, 1F, 1F, 1F)
        }
        val gui = DisplayManager.screen
        Legacy2DRenderer.drawRect(
            -10,
            -10,
            gui.width + 10,
            gui.height + 10,
            ColorRGB(0, 0, 0, 80)
        )
    }

    private fun drawImage(mouseX: Int, mouseY: Int, block: () -> Unit) {
        val sr = ResolutionHelper.resolution
        currentX += ((mouseX - sr.scaledWidth / 2f - currentX) / sr.scaleFactor) * 0.3f
        currentY += ((mouseY - sr.scaledHeight / 2f - currentY) / sr.scaleFactor) * 0.3f
        matrix {
            translate(currentX / 100f, currentY / 100f, 0f) {
                block()
            }
        }
    }

    private fun renderShader() {
        val width = mc.displayWidth.toFloat()
        val height = mc.displayHeight.toFloat()
        val mouseX = Mouse.getX() - 1.0f
        val mouseY = height - Mouse.getY() - 1.0f
        currentShader.render(width, height, mouseX, mouseY, initTime)
    }

    fun resetBackground() {
        initTime = System.currentTimeMillis()
        currentShader = getShader()
    }

    private fun getShader(): GLSLSandbox {
        val shaderIn = if (shaderMode == ShaderMode.Random) {
            Shaders.values().random().name
        } else shader.name
        return shaderCache.getOrPut(shaderIn) {
            GLSLSandbox("assets/spartan/shader/menu/$shaderIn.fsh")
        }
    }

    enum class RenderMode { Picture, Shader, Video }
    enum class ShaderMode { Specified, Random }
    enum class PictureMode { Static, Slide }
    enum class Shaders {
        BlueGrid,
        BlueLandscape,
        Circuits,
        CubeCave,
        DayNightSwitches,
        GreenNebula,
        GridCave,
        Matrix,
        Meteor,
        Minecraft,
        PurpleGrid,
        PurpleNoise,
        Rainbow,
        RectWaves,
        RedLandscape,
        Sea,
        Space,
        Tube,
        Vortex
    }

}