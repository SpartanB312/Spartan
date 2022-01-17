package net.spartanb312.render.util.dsl

import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.player.InventoryPlayer
import net.spartanb312.render.util.mc

fun <T> T.player(
    entityPlayerSP: EntityPlayerSP? = mc.player,
    block: EntityPlayerSP.(T) -> Unit
) = entityPlayerSP?.block(this)

fun WorldClient?.player(
    entityId: Int,
    block: EntityPlayerSP.(WorldClient) -> Unit
) = this?.getEntityById<EntityPlayerSP>(entityId)?.block(this)

fun EntityPlayerSP.inventory(block: InventoryPlayer.(EntityPlayerSP) -> Unit) = inventory?.block(this)
