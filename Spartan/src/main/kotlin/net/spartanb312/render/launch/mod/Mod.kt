package net.spartanb312.render.launch.mod

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Mod(
    val name: String = "",
    val id: String,
    val version: String = "",
    val description: String = "",
    val mixinFile: String = "",
    val group: String = "",
    val priority: Int = 0
) {
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.CLASS)
    annotation class Extension(
        val name: String,
        val fatherId: String,
        val version: String = "",
        val description: String = ""
    )
}