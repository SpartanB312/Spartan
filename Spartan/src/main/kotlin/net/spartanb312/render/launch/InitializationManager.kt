package net.spartanb312.render.launch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.minecraft.launchwrapper.Launch
import net.minecraft.launchwrapper.LaunchClassLoader
import net.spartanb312.render.Spartan
import net.spartanb312.render.Spartan.DEFAULT_FILE_GROUP
import net.spartanb312.render.core.common.ClassUtils.getAnnotatedClasses
import net.spartanb312.render.core.common.extension.remove
import net.spartanb312.render.core.event.inner.MainEventBus
import net.spartanb312.render.core.io.*
import net.spartanb312.render.core.reflect.getOrCreateKotlinInstance
import net.spartanb312.render.features.event.client.InitEvent
import net.spartanb312.render.launch.MixinLoader.loadMixins
import net.spartanb312.render.launch.mod.*
import java.io.File
import java.io.FileInputStream
import java.util.zip.ZipInputStream

@Suppress("DEPRECATION")
object InitializationManager {

    const val SCAN_GROUP = "net.spartanb312"
    private const val MINECRAFT_MODS_GROUP = "mods/"
    private const val SPARTAN_EXTENSIONS_GROUP = DEFAULT_FILE_GROUP + "extensions/"
    private const val SPARTAN_MODS_GROUP = DEFAULT_FILE_GROUP + "mods/"
    private const val SPARTAN_RESOURCES_GROUP = DEFAULT_FILE_GROUP + "resources/"

    private val mods = mutableSetOf<LoadableMod>()
    private val extensions = mutableSetOf<LoadableMod.ExtensionDLC>()

    var isCompatibilityMode = false; private set

    //Exclusions
    private val excludedClasses = mutableSetOf("WorldListener")

    private val excludedPackage = mutableSetOf(
        "net.spartanb312.render.features.ui.wrapper",
        "net.spartanb312.render.mixin"
    )

    private val SCAN_EXCLUSION: (String) -> Boolean = {
        excludedClasses.none { ex -> it.contains(ex) }
                && excludedPackage.none { ex -> it.startsWith(ex) }
                && DEFAULT_INIT_SCAN_EXCLUSION.invoke(it)
    }

    val DEFAULT_INIT_SCAN_EXCLUSION: String.() -> Boolean = {
        !(startsWith("java.")
                || startsWith("sun")
                || startsWith("org.lwjgl")
                || startsWith("org.apache.logging")
                || startsWith("net.minecraft")
                || contains("mixin"))
    }

    var isReady = false
        private set

    @Suppress("UNCHECKED_CAST")
    private val resourceCache = LaunchClassLoader::class.java.getDeclaredField("resourceCache").let {
        it.isAccessible = true
        it[Launch.classLoader] as MutableMap<String, ByteArray>
    }

    @Volatile
    private var called = false

    private fun scanJars() {
        if (called) return else called = true

        fun File.resolveModJar(isSpartanGroup: Boolean = false) {
            val classes = mutableSetOf<Class<*>>()
            RawResourceCache(this.name).build { cache ->
                ZipInputStream(FileInputStream(this)).use { zipStream ->
                    val launchClasses = mutableSetOf<String>()
                    val classesInJar = mutableMapOf<String, ByteArray>()
                    while (true) {
                        val zipEntry = zipStream.nextEntry
                        if (zipEntry == null) break
                        else if (zipEntry.name.endsWith(MF_SUFFIX, true)) {
                            zipStream.readBytes().toTempFileURL()?.openStream()?.readLines()?.asSequence()
                                ?.filter { it.startsWith("LaunchClass") || it.startsWith("ExcludedClasses") }
                                ?.forEach {
                                    try {
                                        when {
                                            it.startsWith("LaunchClass:") -> {
                                                launchClasses.add(it.substringAfter("LaunchClass: ").remove(" "))
                                            }
                                            it.startsWith("LaunchClasses:") -> {
                                                it.substringAfter("LaunchClasses:").apply {
                                                    split(",").forEach { unit ->
                                                        unit.apply {
                                                            if (contains("'")) {
                                                                launchClasses.add(substringAfter("'").substringBefore("'"))
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            it.startsWith("ExcludedClasses") -> {
                                                it.substringAfter("ExcludedClasses:").apply {
                                                    split(",").forEach { unit ->
                                                        unit.apply {
                                                            if (contains("'")) {
                                                                excludedClasses.add(
                                                                    substringAfter("'").substringBefore(
                                                                        "'"
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            it.startsWith("ExcludedPackages") -> {
                                                it.substringAfter("ExcludedPackages:").apply {
                                                    split(",").forEach { unit ->
                                                        unit.apply {
                                                            if (contains("'")) {
                                                                excludedPackage.add(
                                                                    substringAfter("'").substringBefore(
                                                                        "'"
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (exception: Exception) {
                                        exception.printStackTrace()
                                        Logger.fatal("Illegal format!")
                                    }
                                }
                        } else if (!zipEntry.name.endsWith(CLASS_SUFFIX, true)) {
                            val tempBytes = zipStream.readBytes()
                            ResourceCenter.cacheResource(zipEntry.name, tempBytes)
                            cache.resources[zipEntry.name] = tempBytes

                        } else if (zipEntry.name.endsWith(CLASS_SUFFIX, true)) {
                            val tempClassName = zipEntry.name.removeSuffix(".class").replace("/", ".")
                            classesInJar[tempClassName] = zipStream.readBytes()
                            cache.classes.add(tempClassName)
                        }
                    }

                    if (isSpartanGroup || launchClasses.size > 0) {
                        classesInJar.forEach { (name, bytes) ->
                            resourceCache[name] = bytes
                            if (isSpartanGroup) {
                                classes.add(Class.forName(name))
                            }
                        }
                        if (launchClasses.size > 0) {
                            val resourceManagerName = try {
                                Class.forName(launchClasses.first()).getAnnotation(Mod::class.java)!!.id
                            } catch (ignore: Exception) {
                                launchClasses.first().substringAfterLast(".")
                            }
                            cache.validate(resourceManagerName)
                            launchClasses.forEach { it.tryInitMod() }
                        }
                    }
                }

                if (isSpartanGroup) classes.getAnnotatedClasses<Mod>(block = SCAN_EXCLUSION).forEach { clazz ->
                    clazz.name.tryInitMod()
                } else SCAN_GROUP.getAnnotatedClasses<Mod>(block = SCAN_EXCLUSION).forEach {
                    it.name.tryInitMod()
                }
            }
        }

        //Parallel load mod
        runBlocking {
            MINECRAFT_MODS_GROUP.readFiles(JAR_SUFFIX) {
                launch(Dispatchers.IO) {
                    it.resolveModJar()
                }
            }
            SPARTAN_MODS_GROUP.readFiles(JAR_SUFFIX) {
                launch(Dispatchers.IO) {
                    it.resolveModJar(true)
                }
            }
        }

        if (Configs.extensions && !Configs.compatibilityMode) findExtensions()
    }

    private fun findExtensions() {
        val classes = mutableSetOf<Class<*>>()

        runBlocking {
            SPARTAN_EXTENSIONS_GROUP.readFiles(JAR_SUFFIX) { file ->
                launch(Dispatchers.IO) {
                    ZipInputStream(FileInputStream(file)).use { zipStream ->
                        while (true) {
                            val zipEntry = zipStream.nextEntry
                            if (zipEntry == null) break
                            else if (zipEntry.name.endsWith(CLASS_SUFFIX, true)) {
                                val tempClassName = zipEntry.name.removeSuffix(".class").replace("/", ".")
                                resourceCache[tempClassName] = zipStream.readBytes()
                                classes.add(Class.forName(tempClassName))
                            } else {
                                ResourceCenter.cacheResource(zipEntry.name, zipStream.readBytes())
                            }
                        }
                    }
                }
            }
        }

        mods.forEach { mod ->
            if (mod.group != "") {
                classes.getAnnotatedClasses<Mod.Extension> {
                    SCAN_EXCLUSION.invoke(it) && it.startsWith(mod.group)
                }.forEach { clazz ->
                    clazz.name.tryInitExtension()
                }
            }
        }
    }

    private fun String.tryInitExtension(forceLoad: Boolean = false): Boolean {
        try {
            Class.forName(this).apply {
                getAnnotation(Mod.Extension::class.java)?.let { annotation ->
                    val exist = extensions.find { it.name == annotation.name && it.fatherId == annotation.fatherId }
                    val father = mods.find {
                        val flag = if (it.instance != null) {
                            Extendable::class.java.isAssignableFrom(it.instance::class.java)
                        } else false
                        it.id == annotation.fatherId && flag
                    }
                    if (father == null) {
                        Logger.fatal("Extension ${annotation.name} is not for any of the loaded mods!")
                        return false
                    }
                    if (exist == null) {

                        fun printInformation() {
                            Logger.info("father: ${annotation.fatherId}")
                            Logger.info("name: ${annotation.name}")
                            Logger.info("version: ${annotation.version}")
                            Logger.info("package: $name")
                        }
                        if (Extension::class.java.isAssignableFrom(this)) {
                            getOrCreateKotlinInstance<Extension>(name)?.let { instance ->
                                extensions.add(LoadableMod.ExtensionDLC(name, annotation, instance, instance).also {
                                    father.extensions.add(it)
                                    if (father.instance is Extendable) father.instance.extensions.add(it)
                                    if (forceLoad && !it.loaded) {
                                        it.loaded = true
                                        instance.init()
                                    }
                                })
                                Logger.info("Loaded a SpartanDLC assigned from Extension")
                                printInformation()
                            }
                        } else {
                            val instance = getOrCreateKotlinInstance<Any>(name)
                            extensions.add(LoadableMod.ExtensionDLC(name, annotation, instance).also {
                                father.extensions.add(it)
                                if (father.instance is Extendable) father.instance.extensions.add(it)
                            })
                            Logger.info("Loaded a SpartanDLC")
                            printInformation()
                        }
                        return true
                    } else {
                        Logger.fatal("There is already a extension called ${exist.name} for ${exist.fatherId}")
                        return false
                    }
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            return false
        }
        return true
    }

    private fun String.tryInitMod(): Boolean {
        mods.find { it.className == this }?.let {
            if (!this.startsWith(SCAN_GROUP))
                Logger.fatal("SpartanMod {name:${it.name};id:${it.id};package:${it.className}} is already initialized!")
            return false
        }
        return try {
            Class.forName(this)?.apply {
                if (isAnnotationPresent(Mod::class.java)) {
                    val annotation = getAnnotation(Mod::class.java)
                    mods.find { it.id == annotation.id }?.let {
                        Logger.fatal("There is already a mod with id ${it.id}")
                        false
                    }
                    fun printInformation() {
                        Logger.info("name: ${if (annotation.name != "") annotation.name else annotation.id}")
                        Logger.info("version: ${annotation.version}")
                        Logger.info("package: $name")
                    }
                    if (Loadable::class.java.isAssignableFrom(this)) {
                        getOrCreateKotlinInstance<Loadable>(name)?.let { instance ->
                            mods.add(LoadableMod(name, annotation, instance, instance))
                            Logger.info("Loaded a SpartanMod assigned from Loadable")
                            printInformation()
                        }
                    } else {
                        val instance = getOrCreateKotlinInstance<Any>(name)
                        mods.add(LoadableMod(name, annotation, instance))
                        Logger.info("Loaded a SpartanMod")
                        printInformation()
                    }
                } else return false
            }
            true
        } catch (exception: Exception) {
            exception.printStackTrace()
            false
        }
    }

    private fun scanResources() {
        fun File.solveResourcePack() {
            RawResourceCache(this.name).build { cache ->
                ZipInputStream(FileInputStream(this)).use { zipStream ->
                    var validated = false
                    while (true) {
                        val zipEntry = zipStream.nextEntry
                        if (zipEntry == null) break
                        else {
                            val tempBytes = zipStream.readBytes()
                            if (zipEntry.name.endsWith(SPARTAN_RESOURCE_INFO_SUFFIX)) {
                                tempBytes.toTempFileURL()
                                    ?.openStream()
                                    ?.readLines()
                                    ?.forEach {
                                        if (!validated && it.startsWith("Name=")) {
                                            validated = true
                                            cache.validate(it.substringAfter("Name="))
                                        }
                                    }
                            }
                            cache.resources[zipEntry.name] = tempBytes
                            ResourceCenter.cacheResource(zipEntry.name, tempBytes)
                        }
                    }
                }
            }
        }

        MINECRAFT_MODS_GROUP.readFiles(SPARTAN_RESOURCE_SUFFIX) { it.solveResourcePack() }
        SPARTAN_MODS_GROUP.readFiles(SPARTAN_RESOURCE_SUFFIX) { it.solveResourcePack() }
        SPARTAN_EXTENSIONS_GROUP.readFiles(SPARTAN_RESOURCE_SUFFIX) { it.solveResourcePack() }
        SPARTAN_RESOURCES_GROUP.readFiles(SPARTAN_RESOURCE_SUFFIX) { it.solveResourcePack() }
    }

    @JvmStatic
    fun initMod(name: String): Boolean = !started && name.tryInitMod()

    @JvmStatic
    fun initExtension(name: String): Boolean = name.tryInitExtension(true)

    @Volatile
    private var started = false

    @JvmStatic
    fun init() {
        if (Configs.compatibilityMode) {
            Logger.warn("Spartan is running on compatibility mode!")
            isCompatibilityMode = true
            compatibilityMode()
        } else normalLoad()
    }

    private fun normalLoad() {
        scanJars()
        scanResources()
        mods.forEach {
            if (it.priority == Int.MAX_VALUE && !it.isCore) it.priority = Int.MAX_VALUE - 1
        }
        mods.sortedByDescending { it.priority + if (it.isCore) Int.MAX_VALUE else 0 + it.coreModPriority }
        System.gc()
        started = true
        mods.forEach { (it.instance ?: it.loadableInstance)?.apply { MainEventBus.subscribe(this) } }
        mods.loadMixins()
    }

    private fun compatibilityMode() {
        scanResources()
        mods.add(
            LoadableMod(
                Spartan::class.java.name,
                Spartan::class.java.getAnnotation(Mod::class.java),
                Spartan,
                Spartan,
                Spartan::class.java.getAnnotation(CoreMod::class.java)
            )
        )
    }

    @JvmStatic
    fun onTweak() {
        mods.forEach { it.loadableInstance?.onTweak() }
        InitEvent.Tweak.post()
    }

    @JvmStatic
    fun onPreInit() {
        mods.forEach { it.loadableInstance?.preInit() }
        InitEvent.Pre.post()
    }

    @JvmStatic
    fun onInit() {
        mods.forEach { it.loadableInstance?.init() }
        InitEvent.Init.post()
    }

    @JvmStatic
    fun onPostInit() {
        mods.forEach { it.loadableInstance?.postInit() }
        InitEvent.Post.post()
    }

    @JvmStatic
    fun onReady() {
        mods.forEach { it.loadableInstance?.onReady() }
        extensions.forEach {
            if (!it.loaded) {
                it.loaded = true
                it.extensionInstance?.init()
            }
        }
        InitEvent.Ready.post()
        isReady = true
    }

}