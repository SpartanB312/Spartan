package net.spartanb312.render.features.event.world

import net.minecraft.world.World
import net.spartanb312.render.core.event.Cancellable
import net.spartanb312.render.features.manager.ingame.WorldListener
import net.spartanb312.render.launch.Logger

sealed class WorldEvent(val world: World) : Cancellable() {

    class Unload(world: World) : WorldEvent(world)
    class Load(world: World) : WorldEvent(world)
    class Save(world: World) : WorldEvent(world)

    companion object {
        @JvmStatic
        fun postLoad(world: World) {
            if (world.isRemote) {
                world.addEventListener(WorldListener)
            }
            Load(world).post()
            Logger.fatal("Load world")
        }

        @JvmStatic
        fun postUnload(world: World) {
            if (world.isRemote) {
                world.removeEventListener(WorldListener)
            }
            Unload(world).post()
            Logger.fatal("Unload world")
        }

        @JvmStatic
        fun postSave(world: World) {
            Save(world).post()
            Logger.fatal("Save world")
        }
    }

}
