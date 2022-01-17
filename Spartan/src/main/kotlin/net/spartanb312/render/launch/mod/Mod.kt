package net.spartanb312.render.launch.mod

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Mod(
    val name: String = "",
    val id: String,
    val version: String = "",
    val description: String = "",
    val mixinFile: String = "",
    val group: String = ""
) {
    annotation class Extension(
        val name: String,
        val fatherId: String,
        val version: String = "",
        val description: String = ""
    )
}