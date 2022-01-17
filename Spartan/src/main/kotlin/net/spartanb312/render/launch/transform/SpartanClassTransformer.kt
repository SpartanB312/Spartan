package net.spartanb312.render.launch.transform

import net.minecraft.launchwrapper.IClassTransformer

class SpartanClassTransformer : IClassTransformer {

    override fun transform(name: String, transformedName: String, basicClass: ByteArray): ByteArray {
        return basicClass
    }

}