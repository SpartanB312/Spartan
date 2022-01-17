package net.spartanb312.hyperlethal

import net.spartanb312.hyperlethal.HyperLethal.MOD_ID
import net.spartanb312.hyperlethal.HyperLethal.MOD_NAME
import net.spartanb312.hyperlethal.HyperLethal.MOD_VERSION
import net.spartanb312.render.launch.Logger
import net.spartanb312.render.launch.mod.Extendable
import net.spartanb312.render.launch.mod.Loadable
import net.spartanb312.render.launch.mod.LoadableMod
import net.spartanb312.render.launch.mod.Mod

@Mod(
    name = MOD_NAME,
    id = MOD_ID,
    version = MOD_VERSION,
    description = "A utility mod for minecraft"
)
object HyperLethal : Loadable, Extendable {

    const val MOD_NAME = "HyperLethal"
    const val MOD_ID = "hyperlethal"
    const val MOD_VERSION = "3.0.220114"

    override val extensions = mutableSetOf<LoadableMod.ExtensionDLC>()

    override fun onTweak() {
        Logger.fatal("Tweaking HyperLethal")
    }

    override fun preInit() {

    }

    override fun init() {

    }

    override fun postInit() {

    }

    override fun onReady() {

    }

}