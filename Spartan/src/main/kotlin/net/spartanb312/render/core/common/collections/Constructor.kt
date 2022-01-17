package net.spartanb312.render.core.common.collections

class ListScope<T> {
    val mutableList = mutableListOf<T>()
    fun yield(value: T) = mutableList.add(value)
}

fun <T> list(block: ListScope<T>.() -> Unit): List<T> = ListScope<T>().let {
    it.block()
    it.mutableList
}