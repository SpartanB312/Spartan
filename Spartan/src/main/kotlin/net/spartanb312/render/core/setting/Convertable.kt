package net.spartanb312.render.core.setting

interface Convertable<T> {

    val converter: T.() -> String

    val parser: String.() -> T

}