package net.spartanb312.render.core.event.inner

import kotlinx.coroutines.CoroutineScope
import net.spartanb312.render.core.common.thread.newCoroutineScope

object SpartanScope : CoroutineScope by newCoroutineScope(
    nThreads = Runtime.getRuntime().availableProcessors(),
    name = "Spartan Scope"
)
