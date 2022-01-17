package net.spartanb312.render.launch.classloader

import net.spartanb312.render.core.common.collections.list
import net.spartanb312.render.core.io.CLASS_SUFFIX
import net.spartanb312.render.core.io.nextTempFile
import java.io.*
import java.net.URL
import java.net.URLClassLoader
import java.net.URLConnection
import java.security.CodeSigner
import java.security.CodeSource
import java.util.zip.ZipInputStream

/**
 * Author B_312
 * Loading classes for SpartanMod
 * Still have bugs
 */
object SpartanClassLoader : URLClassLoader(arrayOf()) {

    private val sources = mutableListOf<URL>()
    private val cachedResources = mutableMapOf<String, URL>()
    private val cachedClassesResources = mutableMapOf<String, Pair<URL, ByteArray>>()
    private val loadedClasses = mutableMapOf<String?, Class<*>>()

    inline fun <reified T> String.getAllAnnotatedClasses(
        startWith: Array<String> = arrayOf(
            "java.",
            "sun",
            "org.lwjgl",
            "org.apache.logging",
            "net.minecraft",
            "mixin"
        ),
        crossinline block: (String) -> Boolean = { true }
    ): List<Class<*>> where T : Annotation {
        return list {
            getClasses(this@getAllAnnotatedClasses) {
                startWith.none { ex -> it.startsWith(ex) } && block(it)
            }.filter {
                it.isAnnotationPresent(T::class.java)
            }.forEach {
                yield(it)
            }
        }
    }

    fun getClasses(prefix: String, predicate: (String) -> Boolean = { true }): List<Class<*>> = list {
        cachedClassesResources.keys.forEach { name ->
            //Logger.fatal("Find class $name")
            if (name.startsWith(prefix) && predicate(name)) {
                findClass(name)?.let {
                    yield(it)
                }
            }
        }
    }

    override fun findClass(name: String?): Class<*>? {
        //If this class is already loaded then we return it
        val loaded = loadedClasses[name]
        if (loaded != null) return loaded

        //Find the cached resources to define class
        val cache = cachedClassesResources[name]
        if (cache != null) {
            val cs = CodeSource(cache.first, null as Array<out CodeSigner>?)
            val clazz: Class<*> = defineClass(name, cache.second, 0, cache.second.size, cs)
            //Logger.fatal("Defined class $name")
            loadedClasses[name] = clazz
            return clazz
        }
        return super.findClass(name)
    }

    override fun findResource(name: String?): URL? {
        val cache = cachedResources[name]
        if (cache != null) return cache
        return super.findResource(name)
    }

    override fun addURL(url: URL) {
        sources.add(url)
        super.addURL(url)
    }

    fun loadFromJarFile(jarFile: File) = loadFromInputStream(FileInputStream(jarFile))

    private fun findCodeSourceConnectionFor(name: String): URLConnection? {
        val resource = findResource(name)
        return if (resource != null) {
            try {
                resource.openConnection()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        } else null
    }

    fun loadFromInputStream(inputStream: InputStream) =
        ZipInputStream(inputStream).use { zipStream ->
            while (true) {
                val zipEntry = zipStream.nextEntry
                if (zipEntry == null) break
                else if (zipEntry.name.endsWith(CLASS_SUFFIX, true)) {
                    injectClass(zipEntry.name, zipStream.readBytes())
                    //Logger.fatal("Injected class ${zipEntry.name}")
                } else {
                    injectResource(zipEntry.name, zipStream.readBytes())
                }
            }
        }


    fun injectClass(name: String, bytes: ByteArray) {
        injectResource(name, bytes)?.let {
            cachedClassesResources[name.removeSuffix(".class").replace("/", ".")] = Pair(it, bytes)
        }
    }

    fun injectResource(name: String, bytes: ByteArray): URL? {
        nextTempFile.apply {
            return try {
                this.toURI().toURL().also { url ->
                    addURL(url)
                    FileOutputStream(this).let {
                        it.write(bytes)
                        it.flush()
                        it.close()
                        this.deleteOnExit()
                    }
                    cachedResources[name] = url
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                null
            }
        }
    }

}