package net.spartanb312.render.core.io

import net.spartanb312.render.core.common.extension.randomString
import java.io.File

const val JAR_SUFFIX = ".jar"
const val ZIP_SUFFIX = ".zip"
const val CLASS_SUFFIX = ".class"
const val JSON_SUFFIX = ".json"
const val MF_SUFFIX = ".MF"

//Spartan format
const val SPARTAN_RESOURCE_SUFFIX = ".spres"
const val SPARTAN_RESOURCE_INFO_SUFFIX = "sprinfo"

private val TEMP_DIR: String = System.getProperty("java.io.tmpdir")

fun String.readFiles(suffix: String, ignoreCase: Boolean = true, block: (File) -> Unit) {
    File(this).also {
        if (!it.exists()) {
            kotlin.runCatching {
                it.mkdirs()
                it.createNewFile()
            }
            return
        }
        if (it.isDirectory) {
            it.list()?.forEach { it2 ->
                File("$this\\$it2").let { it3 ->
                    if (it3.isDirectory) "$this\\$it2".readFiles(suffix, ignoreCase, block)
                    else if (it3.name.endsWith(suffix, ignoreCase)) try {
                        block(it3)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                }
            }
        } else if (it.name.endsWith(suffix, ignoreCase)) try {
            block(it)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}

val nextTempFile get() = File(TEMP_DIR, "+~JF${randomString(18)}" + ".tmp")
