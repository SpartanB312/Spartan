package net.spartanb312.render.features.language.text

import kotlin.reflect.KProperty

class TextContainer(val containerName: String, var defaultText: String) {

    val textMap = mutableMapOf<String, String>().also {
        it[ENGLISH_UK] = defaultText
    }

    var currentText = defaultText
        private set

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

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return currentText
    }

}