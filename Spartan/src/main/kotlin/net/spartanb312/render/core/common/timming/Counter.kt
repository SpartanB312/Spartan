package net.spartanb312.render.core.common.timming

import java.util.concurrent.atomic.AtomicInteger

class Counter(private val delay: Int) {

    private var counter = 0
    private var last = 0
    private val timer = TickTimer()

    fun invoke() {
        if (timer.tick(delay)) {
            timer.reset()
            last = counter
            counter = 0
        } else counter++
    }

    val counts get() = if (timer.tick(delay * 2)) 0 else last

}

class AtomicCounter(private val delay: Int) {

    private val counter = AtomicInteger(0)
    private val last = AtomicInteger(0)
    private val timer = TickTimer()

    fun invoke() {
        if (timer.tick(delay)) {
            timer.reset()
            last.set(counter.getAndSet(0))
        } else counter.getAndIncrement()
    }

    val counts get() = if (timer.tick(delay * 2)) 0 else last.get()

}