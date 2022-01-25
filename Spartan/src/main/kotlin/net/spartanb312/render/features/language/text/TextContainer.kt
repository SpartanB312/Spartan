package net.spartanb312.render.features.language.text

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class TextContainer(val containerName: String, var defaultText: String) : ReadWriteProperty<Any?, String> {

    val textMap = mutableMapOf<String, String>().also {
        it[ENGLISH_UK] = defaultText
    }

    private var currentText = defaultText

    fun reset() {
        currentText = defaultText
    }

    fun setLang(langName: String): Boolean = textMap[langName].let {
        if (it == null) false
        else {
            currentText = it
            true
        }
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        currentText = value
    }

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return currentText
    }

}