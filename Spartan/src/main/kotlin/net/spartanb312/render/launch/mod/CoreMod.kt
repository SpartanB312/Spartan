package net.spartanb312.render.launch.mod

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class CoreMod(
    val priority: Int = 0
)