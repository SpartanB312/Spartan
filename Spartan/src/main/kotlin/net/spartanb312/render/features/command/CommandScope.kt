package net.spartanb312.render.features.command

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.spartanb312.render.core.common.extension.runIf
import net.spartanb312.render.core.common.math.Vec2d

/**
 * Author B_312
 * Since 01/03/2022
 */
@DslMarker
annotation class CommandScope

class ExecutionScope(val args: Array<String>, val command: Command, val index: Int = 0)

@CommandScope
inline fun ExecutionScope.match(
    check: String,
    ignoreCase: Boolean = true,
    ignoreException: Boolean = true,
    block: ExecutionScope.() -> Unit
): Boolean = onCall(ignoreException, block) { it.equals(check, ignoreCase) }

@CommandScope
inline fun ExecutionScope.matchAny(
    vararg alias: String,
    ignoreCase: Boolean = true,
    ignoreException: Boolean = true,
    block: ExecutionScope.() -> Unit
): Boolean = onCall(ignoreException, block) { alias.any { it2 -> it.equals(it2, ignoreCase) } }

@CommandScope
inline fun ExecutionScope.onCall(
    ignoreException: Boolean,
    block: ExecutionScope.() -> Unit,
    predicate: (String) -> Boolean = { true }
): Boolean = args.getOrNull(index)?.let {
    if (predicate(it)) {
        try {
            ExecutionScope(args, command, index + 1).block()
            true
        } catch (exception: Exception) {
            if (!ignoreException) exception.printStackTrace()
            command.showSyntax()
            false
        }
    } else false
} ?: false

@CommandScope
inline val ExecutionScope.stringValue
    get() = this.args.getOrNull(index)

@CommandScope
inline val ExecutionScope.intValue
    get() = this.args.getOrNull(index)?.let {
        try {
            it.toInt()
        } catch (ignore: Exception) {
            null
        }
    }

@CommandScope
inline val ExecutionScope.doubleValue
    get() = this.args.getOrNull(index)?.let {
        try {
            it.toDouble()
        } catch (ignore: Exception) {
            null
        }
    }

@CommandScope
inline val ExecutionScope.vec2dValue
    get() = if (args.size < index + 2) null
    else try {
        Vec2d(args[index].toDouble(), args[index + 1].toDouble())
    } catch (ignore: Exception) {
        null
    }

@CommandScope
inline val ExecutionScope.vec3dValue
    get() = if (args.size < index + 3) null
    else try {
        Vec3d(args[index].toDouble(), args[index + 1].toDouble(), args[index + 2].toDouble())
    } catch (ignore: Exception) {
        null
    }

@CommandScope
inline val ExecutionScope.blockPosValue
    get() = if (args.size < index + 3) null
    else try {
        BlockPos(args[index].toDouble(), args[index + 1].toDouble(), args[index + 2].toDouble())
    } catch (ignore: Exception) {
        null
    }

@CommandScope
inline fun ExecutionScope.string(
    ignoreException: Boolean = true,
    block: ExecutionScope.(String) -> Unit
): Boolean = stringValue?.let {
    execute(it, ignoreException, block)
} ?: false

@CommandScope
inline fun ExecutionScope.int(
    ignoreException: Boolean = true,
    block: ExecutionScope.(Int) -> Unit,
): Boolean = intValue?.let {
    execute(it, ignoreException, block)
} ?: false

@CommandScope
inline fun ExecutionScope.double(
    ignoreException: Boolean = true,
    block: ExecutionScope.(Double) -> Unit,
): Boolean = doubleValue?.let {
    execute(it, ignoreException, block)
} ?: false

@CommandScope
inline fun ExecutionScope.vec2d(
    ignoreException: Boolean = true,
    block: ExecutionScope.(Vec2d) -> Unit,
): Boolean = vec2dValue?.let {
    execute(it, ignoreException, block)
} ?: false

@CommandScope
inline fun ExecutionScope.vec3d(
    ignoreException: Boolean = true,
    block: ExecutionScope.(Vec3d) -> Unit,
): Boolean = vec3dValue?.let {
    execute(it, ignoreException, block)
} ?: false

@CommandScope
inline fun ExecutionScope.blockPos(
    ignoreException: Boolean = true,
    block: ExecutionScope.(BlockPos) -> Unit,
): Boolean = blockPosValue?.let {
    execute(it, ignoreException, block)
} ?: false

@CommandScope
inline fun <T> ExecutionScope.execute(
    value: T,
    ignoreException: Boolean = true,
    block: ExecutionScope.(T) -> Unit = {}
): Boolean = try {
    ExecutionScope(args, command, index + 1).block(value)
    true
} catch (exception: Exception) {
    if (!ignoreException) exception.printStackTrace()
    command.showSyntax()
    false
}

@CommandScope
inline fun Boolean.onFail(
    block: () -> Unit
) = runIf(!this) { block() }