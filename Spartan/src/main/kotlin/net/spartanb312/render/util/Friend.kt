package net.spartanb312.render.util

import net.spartanb312.render.core.common.collections.list
import net.spartanb312.render.core.setting.Convertable

class Friend(val name: String, private val teams: MutableSet<String> = mutableSetOf()) : Convertable<Friend> {

    companion object {

        fun converter0(): Friend.() -> String = {
            val str = StringBuilder()
            str.append("$name:")
            teams.forEachIndexed { index, it ->
                if (index != teams.size - 1) str.append("$it+")
                else str.append(it)
            }
            str.toString()
        }

        fun parser0(): String.() -> Friend = {
            val name = substringBefore(":")
            val teamData = substringAfter(":", "")
            val set = if (teamData != "") {
                list<String> {
                    if (contains("+")) {
                        split("+").forEach {
                            yield(it)
                        }
                    } else yield(teamData)
                }.toMutableSet()
            } else mutableSetOf()
            Friend(name, set)
        }

    }

    override val converter: Friend.() -> String = converter0()
    override val parser: String.() -> Friend = parser0()

    fun reset(list: List<String>): Friend = this.also {
        teams.clear()
        teams.addAll(list)
    }

    fun inTeam(teamName: String): Boolean = teams.contains(teamName)

    fun addToTeam(teamName: String): Boolean = teams.add(teamName)

    fun removeFromTeam(teamName: String): Boolean = teams.remove(teamName)

}