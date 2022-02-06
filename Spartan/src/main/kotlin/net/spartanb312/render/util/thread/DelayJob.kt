package net.spartanb312.render.util.thread

import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.features.SpartanCore

class DelayJob(
    private val mode: SpartanCore.Executors,
    private val delayTime: Int,
    task: () -> Unit
) : SpartanJob(task) {

    private val timer = TickTimer()

    //Return : launched
    fun tryLaunch(): Boolean {
        if (state == State.Waiting && timer.tick(delayTime)) {
            launch()
            SpartanCore.addTask(mode, this)
            return true
        }
        return false
    }

    override fun reset() {
        super.reset()
        if (state == State.Waiting) {
            timer.reset()
            SpartanCore.addScheduledTask(this)
        }
    }

}