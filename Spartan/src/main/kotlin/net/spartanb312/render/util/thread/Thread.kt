package net.spartanb312.render.util.thread

import net.spartanb312.render.Spartan
import net.spartanb312.render.features.SpartanCore

val isMainThread get() = Thread.currentThread() == Spartan.mainThread
val isSpartanThread get() = Thread.currentThread() == SpartanCore.spartanThread