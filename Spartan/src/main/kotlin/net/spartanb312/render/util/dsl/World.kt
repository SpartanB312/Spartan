package net.spartanb312.render.util.dsl

import net.minecraft.client.multiplayer.WorldClient
import net.spartanb312.render.util.mc

fun <T> T.world(block: WorldClient.(T) -> Unit) = mc.world?.block(this)