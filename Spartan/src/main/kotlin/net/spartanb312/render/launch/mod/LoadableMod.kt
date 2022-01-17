package net.spartanb312.render.launch.mod

class LoadableMod(
    val className: String,
    private val annotation: Mod,
    val instance: Any? = null,
    val loadableInstance: Loadable? = null
) {

    val mixinFile get() = annotation.mixinFile
    val description get() = annotation.description
    val version get() = annotation.version
    val name get() = annotation.name
    val id get() = annotation.id
    val group get() = annotation.group

    val extensions = mutableSetOf<ExtensionDLC>()

    class ExtensionDLC(
        val className: String,
        private val annotation: Mod.Extension,
        val instance: Any? = null,
        val extensionInstance: Extension? = null
    ) {
        @Volatile
        var loaded = false
        val description get() = annotation.description
        val version get() = annotation.version
        val name get() = annotation.name
        val fatherId get() = annotation.fatherId
    }

}