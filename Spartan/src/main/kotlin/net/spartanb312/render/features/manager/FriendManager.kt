package net.spartanb312.render.features.manager

import net.minecraft.entity.Entity
import net.spartanb312.render.Spartan.DEFAULT_CONFIG_GROUP
import net.spartanb312.render.core.config.provider.StandaloneConfigurable
import net.spartanb312.render.features.manager.ConfigManager.registerConfigs
import net.spartanb312.render.util.Friend

/**
 * Author B_312 on 02/12/2022
 */
object FriendManager : StandaloneConfigurable(
    "${DEFAULT_CONFIG_GROUP}managers/",
    "FriendManager"
) {

    init {
        registerConfigs()
    }

    var friends by extendSetting("Friends", mutableListOf(), "Friends and teams", Friend.parser0())

    @JvmStatic
    inline val String.isFriend: Boolean
        get() = friends.any { this.equals(it.name, true) }

    @JvmStatic
    inline val Entity.isFriend: Boolean
        get() = this.name.isFriend

    @JvmStatic
    fun String.inTeam(teamName: String): Boolean = friends.any { this.equals(it.name, true) && it.inTeam(teamName) }

    @JvmStatic
    fun Entity.inTeam(teamName: String): Boolean = this.name.inTeam(teamName)

    fun addFriend(name: String, vararg teams: String): Boolean {
        return if (name.isFriend) false
        else {
            friends = friends.toMutableList().also {
                it.add(
                    Friend(
                        name = name,
                        teams = if (teams.isNotEmpty()) {
                            teams.toMutableSet()
                        } else mutableSetOf()
                    )
                )
            }
            true
        }
    }

    fun removeFriend(name: String): Boolean {
        return if (name.isFriend) {
            friends = friends.toMutableList().also {
                it.removeIf { it2 -> it2.name.equals(name, true) }
            }
            true
        } else false
    }

    private fun findFriend(name: String): Friend? = friends.find { it.name.equals(name, true) }

    const val NOT_FRIEND = 0
    const val ALREADY_IN_TEAM = 1
    const val ADDED_TO_TEAM = 2
    const val NOT_IN_TEAM = 3
    const val REMOVED_FROM_TEAM = 4

    private fun String.addToTeam(teamName: String): Int = findFriend(this)?.let {
        if (it.addToTeam(teamName)) ADDED_TO_TEAM
        else ALREADY_IN_TEAM
    } ?: NOT_FRIEND

    private fun String.removeToTeam(teamName: String): Int = findFriend(this)?.let {
        if (it.removeFromTeam(teamName)) REMOVED_FROM_TEAM
        else NOT_IN_TEAM
    } ?: NOT_FRIEND

}