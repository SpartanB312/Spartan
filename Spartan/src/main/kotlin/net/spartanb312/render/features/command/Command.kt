package net.spartanb312.render.features.command

abstract class Command(
    val name: String,
    val prefix: String,
    val description: String,
    val syntax: String,
) {
    abstract fun ExecutionScope.onCall()
}