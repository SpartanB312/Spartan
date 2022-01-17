package net.spartanb312.render.util.dsl

import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.entity.Entity

inline fun <reified T : Entity?> WorldClient.getEntityById(entityId: Int): T? =
    getEntityByID(entityId)?.takeIf { it::class.java == T::class.java } as T?

fun <T, U : Entity> T.entity(
    entity: U?,
    block: U.(T) -> Unit
) = entity?.block(this)

inline fun <reified U : Entity> WorldClient?.entity(
    entityId: Int,
    block: U.(WorldClient) -> Unit
) = this?.getEntityById<U>(entityId)?.block(this)