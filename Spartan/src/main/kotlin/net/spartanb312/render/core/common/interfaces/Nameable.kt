package net.spartanb312.render.core.common.interfaces

interface Nameable {
    val name: CharSequence
    val nameAsString: String
        get() = name.toString()
}
