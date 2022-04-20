package net.spartanb312.render.features

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import net.spartanb312.render.Spartan
import net.spartanb312.render.core.common.timming.Counter
import net.spartanb312.render.core.common.timming.TickTimer
import net.spartanb312.render.core.common.timming.passedAndRun
import net.spartanb312.render.core.event.inner.MainEventBus
import net.spartanb312.render.core.event.inner.SpartanScope
import net.spartanb312.render.features.common.SafeScope
import net.spartanb312.render.features.event.client.TickEvent
import net.spartanb312.render.features.manager.MainThreadExecutor
import net.spartanb312.render.util.mc
import net.spartanb312.render.util.thread.*
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


/**
 * The Core of Spartan
 */
object SpartanCore {

    private val registered = CopyOnWriteArraySet<Any>()

    /**
     * Cache the scheduled tasks that will be executed in the SpartanThread
     * Cache the delayed tasks that will be executed in the future
     * Cache the repeat tasks that will be executed regularly in the future
     */
    private val cachedAsyncTaskQueue = LinkedBlockingQueue<SpartanJob>()
    private val cachedDelayTaskSet = CopyOnWriteArraySet<DelayJob>()
    private val cachedRepeatTaskSet = CopyOnWriteArraySet<RepeatJob>()

    private val spartanThreadCounter = Counter(1000)
    val ups get() = spartanThreadCounter.counts

    var tickLength = 50

    /**
     * Launch the SpartanThread
     * This is the center thread of this mod
     */
    val spartanThread = thread(name = "Spartan", start = true) {
        val tickTimer = TickTimer()
        while (true) {
            spartanThreadCounter.invoke()

            /**
             * Solve the cached tasks
             */
            cachedRepeatTaskSet.removeIf(RepeatJob::tryRun)
            cachedDelayTaskSet.removeIf(DelayJob::tryLaunch)
            cachedAsyncTaskQueue.poll()?.execute()

            /**
             * Post async SpartanTick
             */
            tickTimer.passedAndRun(tickLength) {
                TickEvent.Async.Pre.post()
                if (Spartan.isReady) mc.world?.let {
                    SafeScope.updateAsync(it.loadedEntityList.toList(), it.playerEntities.toList())
                }
                TickEvent.Async.Post.post()
            }
        }
    }

    /**
     * Launch a coroutine here
     */
    fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = SpartanScope.launch(context, start, block)

    /**
     * Add a scheduled task in specified thread
     */
    fun addScheduledTask(
        mode: Executors = taskThread,
        delayTime: Int,
        task: () -> Unit
    ): DelayJob = DelayJob(mode, delayTime, task).also {
        cachedDelayTaskSet.add(it)
    }

    fun addScheduledTask(
        job: DelayJob
    ): DelayJob = job.also {
        cachedDelayTaskSet.add(it)
    }

    /**
     * Run a repeatTask
     */
    fun runRepeat(
        mode: Executors = taskThread,
        delayTime: Int,
        suspended: Boolean = false,
        task: () -> Unit
    ): RepeatJob = RepeatJob(mode, delayTime, task).also {
        if (suspended) it.suspend()
        cachedRepeatTaskSet.add(it)
    }

    fun runRepeat(
        job: RepeatJob
    ): RepeatJob = job.also {
        cachedRepeatTaskSet.add(it)
    }

    /**
     * Add a task in specified thread
     */
    fun addTask(mode: Executors = taskThread, task: () -> Unit): SpartanJob =
        SpartanJob(task).also { addTask(mode, it) }

    fun addTask(mode: Executors = taskThread, spartanJob: SpartanJob) {
        when (mode) {
            Executors.Spartan -> {
                spartanJob.launch()
                cachedAsyncTaskQueue.put(spartanJob)
            }
            Executors.Main -> MainThreadExecutor.add {
                spartanJob.launch()
                spartanJob.execute()
            }
            Executors.Coroutines -> SpartanScope.launch {
                spartanJob.launch()
                spartanJob.execute()
            }
        }
    }

    fun onMain(task: () -> Unit): SpartanJob = SpartanJob(task).also {
        if (isMainThread) it.launchAndExecute() else addTask(Executors.Main, task)
    }

    fun onSpartan(task: () -> Unit): SpartanJob = SpartanJob(task).also {
        if (isSpartanThread) it.launchAndExecute() else addTask(Executors.Spartan, it)
    }

    fun onAsync(task: () -> Unit): SpartanJob = SpartanJob(task).also { addTask(Executors.Coroutines, it) }

    private inline val taskThread
        get() = when {
            isMainThread -> Executors.Main
            isSpartanThread -> Executors.Spartan
            else -> Executors.Coroutines
        }

    enum class Executors {
        Main,
        Spartan,
        Coroutines
    }

    fun Any.subscribe() = register(this)

    fun register(obj: Any) {
        registered.add(obj)
        MainEventBus.subscribe(obj)
    }

    fun unregister(obj: Any) {
        registered.remove(obj)
        MainEventBus.unsubscribe(obj)
    }

    fun isRegistered(obj: Any) = registered.contains(obj)

}