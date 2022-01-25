package net.spartanb312.render.core.config

import com.google.gson.*
import java.io.*

@Suppress("NOTHING_TO_INLINE")
abstract class Config(
    private val dirPath: String = "config",
    private val configName: String,
) {

    private inline val savePath get() = "${dirPath.removeSuffix("/")}/${if (configName.contains(".")) configName else "$configName.json"}"
    private val gsonPretty: Gson = GsonBuilder().setPrettyPrinting().create()
    private val jsonParser = JsonParser()

    private var file: File? = null

    abstract fun saveConfig()
    abstract fun loadConfig()
    abstract fun resetConfig()

    protected val configFile
        get() = file ?: File(savePath).also {
            file = it
        }

    protected val jsonMap: Map<String, JsonElement>
        get() {
            val loadJson = BufferedReader(FileReader(configFile))
            val map = mutableMapOf<String, JsonElement>()
            jsonParser.parse(loadJson).asJsonObject.entrySet().forEach {
                map[it.key] = it.value
            }
            loadJson.close()
            return map
        }

    protected fun JsonObject.saveToFile() {
        val saveJSon = PrintWriter(FileWriter(configFile))
        saveJSon.println(gsonPretty.toJson(this))
        saveJSon.close()
    }

}