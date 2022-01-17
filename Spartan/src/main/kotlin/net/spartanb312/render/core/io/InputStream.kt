package net.spartanb312.render.core.io

import java.io.*
import java.net.URL
import java.nio.charset.Charset
import java.util.ArrayList

/**
 * Author B_312
 * Since 1.0.0
 */
fun InputStream.forEachLine(charset: Charset = Charsets.UTF_8, action: (line: String) -> Unit) {
    BufferedReader(InputStreamReader(this, charset)).forEachLine(action)
}

fun InputStream.readLines(charset: Charset = Charsets.UTF_8): List<String> {
    val result = ArrayList<String>()
    forEachLine(charset) { result.add(it); }
    return result
}

fun ByteArray.toTempFileURL(): URL? = nextTempFile.run {
    return try {
        FileOutputStream(this).let {
            it.write(this@toTempFileURL)
            it.flush()
            it.close()
            this.deleteOnExit()
        }
        this.toURI().toURL()
    } catch (exception: Exception) {
        exception.printStackTrace()
        null
    }
}
