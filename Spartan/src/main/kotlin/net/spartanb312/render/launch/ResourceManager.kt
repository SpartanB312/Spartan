package net.spartanb312.render.launch

import net.spartanb312.render.core.io.nextTempFile
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL

const val GLOBAL = "GLOBAL"

object ResourceCenter : ResourceManager(GLOBAL) {

    private val resourceManagers = mutableMapOf<String, ResourceManager>() //JarName ResourceManager
    private val classesNameCache = mutableMapOf<String, String>() //ClassName JarName

    @JvmStatic
    fun newResourceManager(jarName: String, managerName: String, rawResourceCache: RawResourceCache) {
        resourceManagers[jarName] = ResourceManager(managerName).also {
            rawResourceCache.resources.forEach { (resourceName, resourceBytes) ->
                it.cacheResource(resourceName, resourceBytes)
            }
            rawResourceCache.classes.forEach {
                classesNameCache[it] = jarName
            }
        }
    }

    @JvmStatic
    fun getManager(name: String): ResourceManager? {
        if (name == GLOBAL) return this
        val jarName = classesNameCache.getOrDefault(name, GLOBAL)
        return resourceManagers.getOrDefault(jarName, null)
    }

    @JvmStatic
    fun String.getResourceURL(resourceName: String): URL? {
        val targetManager = getManager(this)
        if (targetManager != null) {
            val res = targetManager.getResource(resourceName)
            if (res != null) return res
        } else {
            resourceManagers.values.forEach {
                val res = it.getResource(resourceName)
                if (res != null) return res
            }
        }
        return null
    }

    @JvmStatic
    fun getResourceURL(resourceName: String): URL? = GLOBAL.getResourceURL(resourceName)

    @JvmStatic
    fun String.getResourceAsStream(resourceName: String): InputStream? = this.getResourceURL(resourceName)?.openStream()

    @JvmStatic
    fun getResourceAsStream(resourceName: String): InputStream? = getResourceURL(resourceName)?.openStream()

}

class RawResourceCache(private val jarName: String) {

    private var valid = false
    private lateinit var managerName: String

    val resources = mutableMapOf<String, ByteArray>()
    val classes = mutableSetOf<String>()

    fun validate(managerName: String) {
        valid = true
        this.managerName = managerName
    }

    fun useResourcesIfValid(): RawResourceCache {
        if (valid) {
            ResourceCenter.newResourceManager(jarName, managerName, this)
        }
        return this
    }

}

fun RawResourceCache.build(block: (RawResourceCache) -> Unit): RawResourceCache =
    this.also(block).useResourcesIfValid()

open class ResourceManager(val name: String) {

    private val cachedResources = mutableMapOf<String, URL>()

    fun cacheResource(name: String, bytes: ByteArray) {
        nextTempFile.apply {
            try {
                this.toURI().toURL().also { url ->
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
            }
        }
    }

    fun getResource(name: String): URL? = cachedResources.getOrDefault(name, null)

}

fun URL.asStream(block: (InputStream) -> Unit) = block(openStream())