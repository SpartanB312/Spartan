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
import net.spartanb312.render.launch.ResourceCenter.getSpartanResourceStream
import net.spartanb312.render.launch.mod.Extendable
import net.spartanb312.render.launch.mod.Loadable
import net.spartanb312.render.launch.mod.LoadableMod
import net.spartanb312.render.launch.mod.Mod
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
object Spartan : Loadable, Extendable {

    const val MOD_ID = "spartan"
    const val MOD_NAME = "Spartan"
    const val MOD_VERSION = "0.1"

    const val DEFAULT_FILE_GROUP = "Spartan/"
    const val DEFAULT_CONFIG_GROUP = "${DEFAULT_FILE_GROUP}/configs/"

    val mainThread: Thread = Thread.currentThread()
    override val extensions = mutableSetOf<LoadableMod.ExtensionDLC>()

    var isReady = false
        private set

    override fun onTweak() {
        Logger.info("Spartan Initializing")
    }

    override fun preInit() {
        Logger.info("Pre Init Spartan")
    }

    override fun init() {
        //Set title and icons
        Display.setTitle("$MOD_NAME $MOD_VERSION")
        Display.setIcon(
            arrayOf(
                readImageToBuffer(getSpartanResourceStream("icon/logo32.png")),
                readImageToBuffer(getSpartanResourceStream("icon/logo16.png"))
            )
        )

        //Key Managers
        runBlocking {
            CommandManager.asyncLoad(this)
            ModuleManager.asyncLoad(this)
        }

        //Player Managers
        InputManager.subscribe()
        InventoryManager.subscribe()
        MessageManager.subscribe()

        //Renderer managers
        ResolutionHelper.subscribe()
        MenuDisplayManager.subscribe()
        Render2DManager.subscribe()
        FontManager.subscribe()

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
    }

    private fun AsyncLoadable.asyncLoad(coroutineScope: CoroutineScope) {
        SpartanCore.register(this)
        coroutineScope.launch(Dispatchers.IO) {
            init()
        }
    }

}