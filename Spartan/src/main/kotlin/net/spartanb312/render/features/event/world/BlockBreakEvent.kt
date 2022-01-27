package net.spartanb312.render.features.event.world

import net.minecraft.util.math.BlockPos
import net.spartanb312.render.core.event.Event

class BlockBreakEvent(val breakerID: Int, val position: BlockPos, val progress: Int) : Event()