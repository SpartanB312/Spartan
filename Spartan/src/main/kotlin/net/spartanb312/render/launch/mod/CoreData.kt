package net.spartanb312.render.launch.mod

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class CoreData(
    @get:JvmName("k")
    val kind: Int = 1,

    @get:JvmName("d1")
    val data1: Array<String> = [],

    @get:JvmName("d2")
    val data2: Array<String> = [],

    @get:JvmName("ex")
    val extraData: String = ""
)