package net.spartanb312.render.util.thread

import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.features.SpartanCore
import java.util.concurrent.atomic.AtomicBoolean

class DelayJob(
    private val mode: SpartanCore.Executors,
    private val delayTime: Int,
    task: () -> Unit
) : SpartanJob(task) {

    private val timer = TickTimer()
    private val launched = AtomicBoolean(false)

    fun tryLaunch() {
        if (launched.get() || !timer.tick(delayTime)) return
        if (!launched.getAndSet(true)) SpartanCore.addTask(mode, this)
    }

}