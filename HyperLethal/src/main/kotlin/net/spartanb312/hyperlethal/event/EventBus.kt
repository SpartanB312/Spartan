package net.spartanb312.hyperlethal.event

import kotlinx.coroutines.CoroutineScope
import net.spartanb312.render.core.common.thread.newCoroutineScope
import net.spartanb312.render.core.event.EventBus

object EventBus : EventBus()

object HyperLethalScope : CoroutineScope by newCoroutineScope(
    nThreads = Runtime.getRuntime().availableProcessors(),
    name = "HyperLethal Scope"
)