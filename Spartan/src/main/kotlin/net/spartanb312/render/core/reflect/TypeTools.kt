@file:Suppress("UNCHECKED_CAST")

package net.spartanb312.render.core.reflect

fun <T> Any?.cast(packageName: String): T? = this.takeIf { this?.javaClass?.name == packageName } as T