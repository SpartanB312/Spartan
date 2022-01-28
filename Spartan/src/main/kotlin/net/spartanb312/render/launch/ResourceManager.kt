package net.spartanb312.render.launch

import net.spartanb312.render.core.common.MutablePair
import net.spartanb312.render.core.io.nextTempFile
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL

const val GLOBAL = "GLOBAL"

object ResourceCenter : ResourceManager(GLOBAL) {

    private val resourceManagers = mutableMapOf<String, ResourceManager>() //JarName ResourceManager
    private val classesNameCache = mutableMapOf<String, String>() //ClassName JarName

    private const val RESERVE_TIME = 60000L
    private const val UPDATE_TIME = 60000L

    //Use this to make finding resources faster
    private val frequentlyManagers = mutableMapOf<String, ResourceManager>() //ClassName or ModName ResourceManager
    private var frequentlyResources =
        mutableMapOf<ResourceManager, MutableMap<String, MutablePair<URL, Long>>>() //Resource name

    init {
        Thread {
            while (true) {
                val newMap = mutableMapOf<ResourceManager, MutableMap<String, MutablePair<URL, Long>>>()
                frequentlyResources.forEach { (manager, map) ->
                    val newInnerMap = mutableMapOf<String, MutablePair<URL, Long>>()
                    val currentTime = System.currentTimeMillis()
                    map.forEach { (resName, pair) ->
                        if (pair.second > currentTime) {
                            newInnerMap[resName] = pair
                        }
                    }
                    newMap[manager] = newInnerMap
                }
                frequentlyResources = newMap
                Thread.sleep(UPDATE_TIME)
            }
        }.start()
    }

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

    @JvmStatic//Input : class name or resourceManagerName
    fun getManager(name: String): ResourceManager? {
        if (name == GLOBAL) return this
        val frequentlyUsedResourceManager = frequentlyManagers[name]
        if (frequentlyUsedResourceManager != null) return frequentlyUsedResourceManager
        val potentialManager = resourceManagers.values.find { it.name == name }
        if (potentialManager != null) return potentialManager
        val jarName = classesNameCache.getOrDefault(name, GLOBAL)
        return resourceManagers.getOrDefault(jarName, null)
    }

    @JvmStatic
    fun String.getResourceURL(resourceName: String): URL? {
        val targetManager = getManager(this)
        if (targetManager != null) {
            //Find resource in frequently used resources
            val innerMap = frequentlyResources[targetManager]
            if (innerMap != null) {
                val resInInnerMap = innerMap[resourceName]
                return if (resInInnerMap != null) {
                    //Use the old resource
                    resInInnerMap.second = System.currentTimeMillis() + RESERVE_TIME
                    resInInnerMap.first
                } else {
                    //Find resource in specified resource manager
                    val res = targetManager.getResource(resourceName)
                    //If resource is not null put it in the inner map
                    if (res != null) innerMap[resourceName] =
                        MutablePair(res, System.currentTimeMillis() + RESERVE_TIME)
                    res
                }
            } else {
                val newInnerMap = mutableMapOf<String, MutablePair<URL, Long>>()
                frequentlyResources[targetManager] = newInnerMap
                //Find resource in specified resource manager
                val res = targetManager.getResource(resourceName)
                if (res != null) newInnerMap[resourceName] =
                    MutablePair(res, System.currentTimeMillis() + RESERVE_TIME)
                return res
            }
        } else {
            //Find resource in Global resource manager
            val resInGlobal = getResource(resourceName)
            if (resInGlobal != null) return resInGlobal
            //Find resource in all resource manager
            resourceManagers.values.forEach {
                val res = it.getResource(resourceName)
                if (res != null) return res
            }
        }
        return null
    }

    @JvmStatic
    fun getResourceURL(resourceName: String): URL? = GLOBAL.getResourceURL(resourceName)

    @JvmStatic//(ModName or className).getResourceAsStream()
    fun String.getResourceAsStream(resourceName: String): InputStream? = this.getResourceURL(resourceName)?.openStream()

    @JvmStatic
    fun getResourceAsStream(resourceName: String): InputStream? = getResourceURL(resourceName)?.openStream()

    @JvmStatic
    fun getSpartanResourceStream(resourceName: String): InputStream? =
        getResourceAsStream("assets/spartan/$resourceName")

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