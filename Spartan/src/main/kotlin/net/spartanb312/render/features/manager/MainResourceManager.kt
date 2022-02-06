package net.spartanb312.render.features.manager

import net.spartanb312.render.Spartan
import net.spartanb312.render.launch.ResourceCenter
import java.io.InputStream
import java.net.URL

object MainResourceManager {

    private var resourceManager = ResourceCenter.getManager(Spartan.MOD_ID)!!

    @JvmStatic
    fun getResource(resourceName: String): URL? = resourceManager.getResource(resourceName)

    @JvmStatic
    fun getResourceAsStream(resourceName: String): InputStream? = getResource(resourceName)?.openStream()

    @JvmStatic
    fun getSpartanResourceStream(resourceName: String): InputStream? =
        getResourceAsStream("assets/spartan/$resourceName")

}