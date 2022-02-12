package net.spartanb312.render.core.common

import net.spartanb312.render.core.common.collections.list
import net.spartanb312.render.core.setting.Convertable

class KeyBind(vararg var key: Int, val action: () -> Unit) : Convertable<KeyBind> {

    companion object {

        fun converter0(): KeyBind.() -> String = {
            val str = StringBuilder()
            key.forEachIndexed { index, it ->
                if (index != key.size - 1) str.append("$it+")
                else str.append(it)
            }
            str.toString()
        }

        fun KeyBind.parser0(): String.() -> KeyBind = {
            this@parser0.reset(list {
                if (contains("+")) {
                    split("+").forEach {
                        kotlin.runCatching {
                            yield(it.toInt())
                        }
                    }
                } else yield(toInt())
            })
        }

    }

    override val converter: KeyBind.() -> String = converter0()
    override val parser: String.() -> KeyBind = parser0()

    fun reset(vararg input: Int): KeyBind = this.also {
        key = input
    }

    fun reset(list: List<Int>): KeyBind = this.also {
        key = list.toIntArray()
    }

}