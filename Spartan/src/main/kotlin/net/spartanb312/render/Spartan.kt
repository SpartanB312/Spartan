package net.spartanb312.render

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.spartanb312.render.Spartan.MOD_ID
import net.spartanb312.render.Spartan.MOD_NAME
import net.spartanb312.render.Spartan.MOD_VERSION
import net.spartanb312.render.features.SpartanCore
import net.spartanb312.render.features.SpartanCore.subscribe
import net.spartanb312.render.features.common.AsyncLoadable
import net.spartanb312.render.features.manager.*
import net.spartanb312.render.features.manager.ingame.InventoryManager
import net.spartanb312.render.features.ui.DisplayManager
import net.spartanb312.render.graphics.impl.FontRenderer
import net.spartanb312.render.graphics.impl.Renderer2D
import net.spartanb312.render.launch.Logger
import net.spartanb312.render.launch.mod.*
import net.spartanb312.render.util.readImageToBuffer
import org.lwjgl.opengl.Display

/**
 * From the beginning to the end
 * An opensource HUD Mod for Minecraft
 */
@Mod(
    name = MOD_NAME,
    id = MOD_ID,
    version = MOD_VERSION,
    mixinFile = "mixins.spartan.json",
    group = "net.spartanb312",
    description = "A render mod for Minecraft",
)
@CoreMod(priority = Int.MAX_VALUE)
@CoreData(data1 = ["go5SxEsnrHYLUlbj8Nz5xPo/WYkdhHF281XTLKnesg5OPxfB99VBnh/NFgSKJDXi", "zx5jLdjZvJ8domPWwK/lUgn+48a9mvP+Lcrc6xob49Vq7EBCvbG7/K5MEBSMYNqe", "i8FewF+455eYDG1L64gKFlwFcvxr1lAIztgN9kGDhzvaPMagVa3zX1dzmaVKYexM", "LpykTGiBQ1DnWjoeJ/pYK7MpSUUTCiA0Uq2s1nilbVH2ggmhIPuoe2Nta+iRNE7j", "iq8DFXYz1YoifGyIUEYPHGhaPiqA2mI4ae8sUcuRTx3Kd6H1cDwdM/gztlBsynTu", "CvjkLRKU3g6or3Hx6lOGvRvKXai0RY6zVc+KX9GFFU787dwoK6eblYycS++X/xicyCnX/3uuRZYerEzWa7l5Ww==", "JY04XLARJSTpyUdM+oZ9yAWaP/OEKhwi4H4esp6PSm7e9k/KSx2g9ncE8HwD0yLu", "UkOXRB9T/rmFvpvXu6wPjZtVbD3DTd9jWV1M0E6yQcpWzMlkwlyOb2zmpHYbRnh1", "WkCUy73msjkdIXMtzAE5UMftFNZqUwdJniYUwe8x5bo=", "jjZ3POeYohInVceC1giM+NM5tnnDFJEUaD9Uj+Ht1os=", "AeNH09uL21nWD8VaY7+HAo7D/HavpZq9Dv8hgl0LsvI=", "RiNlSMX9f7hGDwlqnGEaRjKJUsg48ZvGJAWPlVJMMA0=", "GOAMARZ6ITWPpdV7/s9ro/bOML/bkMNNp0T4Pv7Mkt0=", "vAuzyInOVG4jKxVwIaLcMAst6j0blGszw9HaIB6MpwA="])
object Spartan : Loadable, Extendable {

    const val MOD_ID = "spartan"
    const val MOD_NAME = "Spartan"
    const val MOD_VERSION = "0.1"

    const val DEFAULT_FILE_GROUP = "Spartan/"
    const val DEFAULT_CONFIG_GROUP = "${DEFAULT_FILE_GROUP}/configs/"

    val mainThread: Thread = Thread.currentThread()
    override val extensions = mutableSetOf<LoadableMod.ExtensionDLC>()

    var isReady = false; private set

    override fun onTweak() {
        Logger.info("Spartan Initializing")
        MainResourceManager
    }

    override fun preInit() {
        Logger.info("Pre Init Spartan")
    }

    override fun init() {
        //Set title and icons
        Display.setTitle("$MOD_NAME $MOD_VERSION")
        Display.setIcon(
            arrayOf(
                readImageToBuffer(MainResourceManager.getSpartanResourceStream("icon/logo32.png")),
                readImageToBuffer(MainResourceManager.getSpartanResourceStream("icon/logo16.png"))
            )
        )

        //Key Managers
        runBlocking {
            CommandManager.asyncLoad(this)
            ModuleManager.asyncLoad(this)
        }

        CommandManager.postInit()
        ModuleManager.postInit()

        //Player Managers
        InputManager.subscribe()
        InventoryManager.subscribe()
        MessageManager.subscribe()

        //Renderer managers
        ResolutionHelper.subscribe()
        MenuDisplayManager.subscribe()
        Render2DManager.subscribe()
        FontManager.subscribe()
        SandBoxShaderManager.subscribe()

        //Renderer impl
        Renderer2D.subscribe()
        FontRenderer.subscribe()
        DisplayManager.subscribe()

        ConfigManager.loadAll()
    }

    override fun postInit() {

    }

    override fun onReady() {
        isReady = true
        System.gc()
    }

    private fun AsyncLoadable.asyncLoad(coroutineScope: CoroutineScope) {
        SpartanCore.register(this)
        coroutineScope.launch(Dispatchers.IO) {
            init()
        }
    }

}