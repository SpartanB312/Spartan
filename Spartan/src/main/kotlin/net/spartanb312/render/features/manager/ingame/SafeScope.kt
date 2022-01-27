package net.spartanb312.render.features.manager.ingame

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.PlayerControllerMP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.client.network.NetHandlerPlayClient
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.spartanb312.render.core.event.inner.listener
import net.spartanb312.render.features.event.client.ClientTickEvent
import net.spartanb312.render.features.event.network.ConnectionEvent
import net.spartanb312.render.features.event.world.WorldEvent
import net.spartanb312.render.util.mc

class SafeScope(
    val mc: Minecraft,
    val player: EntityPlayerSP,
    val world: WorldClient,
    val entities: List<Entity>,
    val players: List<EntityPlayer>,
    val playerControllerMP: PlayerControllerMP,
    val connection: NetHandlerPlayClient
) {
    companion object {
        var instance: SafeScope? = null
            private set

        var async: SafeScope? = null

        init {
            listener<ConnectionEvent.Disconnect>(Int.MAX_VALUE, true) {
                reset()
            }

            listener<WorldEvent.Unload>(Int.MAX_VALUE, true) {
                reset()
            }

            listener<ClientTickEvent>(Int.MAX_VALUE, true) {
                update()
            }
        }

        fun update() {
            val world = mc.world ?: return
            val player = mc.player ?: return
            val playerController = mc.playerController ?: return
            val connection = mc.connection ?: return
            instance = SafeScope(
                mc,
                player,
                world,
                world.loadedEntityList,
                world.playerEntities,
                playerController,
                connection
            )
        }

        fun updateAsync(entities: List<Entity>, players: List<EntityPlayer>) {
            val world = mc.world ?: return
            val player = mc.player ?: return
            val playerController = mc.playerController ?: return
            val connection = mc.connection ?: return
            instance = SafeScope(
                mc,
                player,
                world,
                entities,
                players,
                playerController,
                connection
            )
        }

        fun reset() {
            instance = null
        }

        fun resetAsync() {
            async = null
        }
    }
}