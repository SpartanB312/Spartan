package net.spartanb312.render.util.asm

import org.objectweb.asm.ClassReader
import org.objectweb.asm.tree.ClassNode

fun ByteArray.getNode(): ClassNode =
    ClassNode().also {
        ClassReader(this).accept(it, 0)
    }
