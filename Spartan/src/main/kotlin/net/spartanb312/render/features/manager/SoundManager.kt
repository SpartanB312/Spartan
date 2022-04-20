package net.spartanb312.render.features.manager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.ISound
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.client.audio.SoundManager
import net.minecraft.client.resources.FileResourcePack
import net.minecraft.client.resources.SimpleReloadableResourceManager
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.spartanb312.render.core.event.inner.SpartanScope
import net.spartanb312.render.core.io.MP3_SUFFIX
import net.spartanb312.render.core.io.SPARTAN_RESOURCE_SUFFIX
import net.spartanb312.render.core.io.readFiles
import net.spartanb312.render.launch.InitializationManager.MINECRAFT_MODS_GROUP
import net.spartanb312.render.launch.InitializationManager.SPARTAN_EXTENSIONS_GROUP
import net.spartanb312.render.launch.InitializationManager.SPARTAN_MODS_GROUP
import net.spartanb312.render.launch.InitializationManager.SPARTAN_RESOURCES_GROUP
import net.spartanb312.render.launch.Logger
import net.spartanb312.render.util.mc
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.ConcurrentHashMap
import java.util.zip.ZipInputStream
import kotlin.math.min

/**
 * Under development
 */
object SoundManager {

    private var currentMusic: ISound? = null
    private var timeUntilNextMusic = 100

    private val cachedSounds = ConcurrentHashMap<String, ISound>()
    private var nextMusic: ISound? = null

    private var isReady = false

    init {
        SpartanScope.launch(Dispatchers.IO) {
            Logger.fatal("StartLoading Files")
            MINECRAFT_MODS_GROUP.readFiles(SPARTAN_RESOURCE_SUFFIX) { loadSounds(it) }
            SPARTAN_MODS_GROUP.readFiles(SPARTAN_RESOURCE_SUFFIX) { loadSounds(it) }
            SPARTAN_EXTENSIONS_GROUP.readFiles(SPARTAN_RESOURCE_SUFFIX) { loadSounds(it) }
            SPARTAN_RESOURCES_GROUP.readFiles(SPARTAN_RESOURCE_SUFFIX) { loadSounds(it) }
            isReady = true
            Logger.fatal("Finished")
        }
    }

    @JvmStatic
    fun update() {
        /*
        if (isReady) {
            if (currentMusic != null) {
                val next = nextMusic
                if (next != null && next != currentMusic && !mc.soundHandler.isSoundPlaying(next)) {
                    currentMusic = next
                    nextMusic = null
                    playMusic0(next)
                }
                if (!mc.soundHandler.isSoundPlaying(currentMusic!!)) {
                    currentMusic = null
                    timeUntilNextMusic = min(1000, timeUntilNextMusic)
                }
            }

            if (currentMusic == null && timeUntilNextMusic-- <= 0) {
                playMusic0(cachedSounds.values.random())
            }
        }
         */
    }

    private fun playMusic0(sound: ISound) {
        currentMusic = sound
        mc.soundHandler.playSound(sound)
    }

    fun loadSounds(file: File) {
        Logger.fatal("Loading file ${file.name}")
        val fileResourcePack = FileResourcePack(file)
        (Minecraft.getMinecraft().resourceManager as SimpleReloadableResourceManager)
            .reloadResourcePack(fileResourcePack)

        ZipInputStream(FileInputStream(file)).use { zipStream ->
            while (true) {
                val zipEntry = zipStream.nextEntry
                if (zipEntry == null) break
                else {
                    if (zipEntry.name.endsWith(MP3_SUFFIX)) {
                        val removed = zipEntry.name.removePrefix("assets/spartan/")
                        val resourceLocation = ResourceLocation("spartan", removed)
                        val event = SoundEvent(resourceLocation)
                        Logger.fatal("new sound $removed")
                        cachedSounds[removed.removeSuffix(MP3_SUFFIX)] = PositionedSoundRecord.getMusicRecord(event)
                    }
                }
            }
        }

        cachedSounds.forEach { (name, _) ->
            Logger.fatal(name)
        }
    }

}