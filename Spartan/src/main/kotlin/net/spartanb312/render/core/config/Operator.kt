package net.spartanb312.render.core.config

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.spartanb312.render.core.common.extension.mapAll
import net.spartanb312.render.core.common.extension.notNull
import net.spartanb312.render.core.common.graphics.ColorRGB
import net.spartanb312.render.core.setting.AbstractSetting
import net.spartanb312.render.core.setting.impl.collections.ListSetting
import net.spartanb312.render.core.setting.impl.collections.MapSetting
import net.spartanb312.render.core.setting.impl.number.DoubleSetting
import net.spartanb312.render.core.setting.impl.number.FloatSetting
import net.spartanb312.render.core.setting.impl.number.IntegerSetting
import net.spartanb312.render.core.setting.impl.number.LongSetting
import net.spartanb312.render.core.setting.impl.other.ColorSetting
import net.spartanb312.render.core.setting.impl.other.ExtendSetting
import net.spartanb312.render.core.setting.impl.primitive.BooleanSetting
import net.spartanb312.render.core.setting.impl.primitive.EnumSetting
import net.spartanb312.render.core.setting.impl.collections.lists.*
import net.spartanb312.render.core.setting.impl.primitive.StringSetting

fun JsonObject.saveSetting(setting: AbstractSetting<*>): JsonObject {
    when (setting) {
        //Numbers
        is DoubleSetting -> addProperty(setting.name, setting.value)
        is FloatSetting -> addProperty(setting.name, setting.value)
        is IntegerSetting -> addProperty(setting.name, setting.value)
        is LongSetting -> addProperty(setting.name, setting.value)
        //Other
        is ColorSetting -> addProperty(setting.name, setting.value.rgba)
        is ExtendSetting -> addProperty(setting.name, setting.convertToString())
        //Primitive
        is BooleanSetting -> addProperty(setting.name, setting.value)
        is StringSetting -> addProperty(setting.name, setting.value)
        is EnumSetting -> addProperty(setting.name, setting.currentName())
        //List
        is ListSetting<*> -> saveList(setting)
        //Map
        is MapSetting<*, *> -> saveMap(setting)
    }
    return this
}

fun JsonObject.saveSettings(settings: List<AbstractSetting<*>>): JsonObject {
    settings.forEach { setting ->
        saveSetting(setting)
    }
    return this
}

fun JsonObject.saveList(setting: ListSetting<*>): JsonObject {
    val array = JsonArray()
    when (setting) {
        is DoubleListSetting -> setting.value.forEach { array.add(it) }
        is FloatListSetting -> setting.value.forEach { array.add(it) }
        is IntegerListSetting -> setting.value.forEach { array.add(it) }
        is LongListSetting -> setting.value.forEach { array.add(it) }
        is StringListSetting -> setting.value.forEach { array.add(it) }
        is ExtendListSetting -> setting.convertToStringList().forEach { array.add(it) }
    }
    this.add(setting.name, array)
    return this
}

fun ListSetting<*>.readList(array: JsonArray) {
    when (this) {
        is DoubleListSetting -> value = array.map { it.asDouble }.toMutableList()
        is FloatListSetting -> value = array.map { it.asFloat }.toMutableList()
        is IntegerListSetting -> value = array.map { it.asInt }.toMutableList()
        is LongListSetting -> value = array.map { it.asLong }.toMutableList()
        is StringListSetting -> value = array.map { it.asString }.toMutableList()
        is ExtendListSetting -> parseStringList(array.map { it.asString }.toMutableList())
    }
}

fun JsonObject.saveMap(setting: MapSetting<*, *>): JsonObject =
    JsonObject().apply {
        setting.getStringMap().forEach {
            addProperty(it.key, it.value)
        }
    }.also { add(setting.name, it) }

fun MapSetting<*, *>.readObject(obj: JsonObject) = readStringMap(obj.entrySet().mapAll { Pair(key, value.asString) })

fun Map<String, JsonElement>.readSetting(setting: AbstractSetting<*>) {
    when (setting) {
        //Numbers
        is DoubleSetting -> this[setting.name].notNull { setting.value = asDouble }
        is FloatSetting -> this[setting.name].notNull { setting.value = asFloat }
        is IntegerSetting -> this[setting.name].notNull { setting.value = asInt }
        is LongSetting -> this[setting.name].notNull { setting.value = asLong }
        //Other
        is ColorSetting -> this[setting.name].notNull { setting.value = ColorRGB(asInt) }
        is ExtendSetting -> this[setting.name].notNull { setting.parseStringAndSet(asString) }
        //Primitive
        is BooleanSetting -> this[setting.name].notNull { setting.value = asBoolean }
        is StringSetting -> this[setting.name].notNull { setting.value = asString }
        is EnumSetting -> this[setting.name].notNull { setting.setWithName(asString) }
        //List
        is ListSetting<*> -> this[setting.name].notNull { setting.readList(asJsonArray) }
        //Map
        is MapSetting<*, *> -> this[setting.name].notNull { setting.readObject(asJsonObject) }
    }
}

fun Map<String, JsonElement>.readSettings(settings: List<AbstractSetting<*>>) {
    settings.forEach { setting ->
        this.readSetting(setting)
    }
}

fun JsonObject.readSetting(setting: AbstractSetting<*>) {
    when (setting) {
        //Numbers
        is DoubleSetting -> this[setting.name].notNull { setting.value = asDouble }
        is FloatSetting -> this[setting.name].notNull { setting.value = asFloat }
        is IntegerSetting -> this[setting.name].notNull { setting.value = asInt }
        is LongSetting -> this[setting.name].notNull { setting.value = asLong }
        //Other
        is ColorSetting -> this[setting.name].notNull { setting.value = ColorRGB(asInt) }
        is ExtendSetting -> this[setting.name].notNull { setting.parseStringAndSet(asString) }
        //Primitive
        is BooleanSetting -> this[setting.name].notNull { setting.value = asBoolean }
        is StringSetting -> this[setting.name].notNull { setting.value = asString }
        is EnumSetting -> this[setting.name].notNull { setting.setWithName(asString) }
        //List
        is ListSetting<*> -> this[setting.name].notNull { setting.readList(asJsonArray) }
        //Map
        is MapSetting<*, *> -> this[setting.name].notNull { setting.readObject(asJsonObject) }
    }
}

fun JsonObject.readSettings(settings: List<AbstractSetting<*>>) {
    settings.forEach { setting ->
        this.readSetting(setting)
    }
}




