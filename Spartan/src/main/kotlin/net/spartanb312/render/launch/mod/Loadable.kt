package net.spartanb312.render.launch.mod

interface Loadable {

    fun onTweak() {}

    fun preInit() {}

    fun init() {}

    fun postInit() {}

    fun onReady() {}

}