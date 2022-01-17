package net.spartanb312.render.features.language

import net.spartanb312.render.Spartan.DEFAULT_FILE_GROUP
import net.spartanb312.render.features.language.text.*
import java.io.File
import java.util.*

object LanguageManager {

    private val registeredTexts = mutableSetOf<TextContainer>()

    private val languageStack = Stack<String>().apply {
        push(CUSTOM)
        push(CHINESE_TW)
        push(CHINESE_CN)
        push(RUSSIAN_RU)
        push(JAPANESE_JP)
        push(ENGLISH_UK)
    }

    fun TextContainer.register(): TextContainer = this.apply { registeredTexts.add(this) }

    fun String.setLanguage() {
        if (languageStack.contains(this)) {
            languageStack.remove(this)
        }
        languageStack.push(this)
        updateLanguages()
    }

    fun loadLanguages() = languageStack.forEach {
        try {
            it.readLanguage()
        } catch (ignore: Exception) {
            it.saveLanguage()
        }
    }

    fun saveLanguages() = languageStack.forEach {
        it.saveLanguage()
    }

    fun resetLanguages() {
        ENGLISH_UK.setLanguage()
        registeredTexts.forEach(TextContainer::reset)
    }

    private fun updateLanguages() = registeredTexts.forEach {
        for (langIndex in languageStack.size - 1 downTo 0) {
            if (it.setLang(languageStack[langIndex])) return@forEach
        }
    }

    private fun String.saveLanguage() = File(DEFAULT_FILE_GROUP + "language/$this.lang").apply {
        if (!exists()) {
            File(DEFAULT_FILE_GROUP + "language").mkdirs()
            createNewFile()
        }
        writeText(sequence {
            registeredTexts.forEach {
                yield(it.containerName + "=" + (it.textMap[this@saveLanguage] ?: "") + "\n")
            }
        }.joinToString(separator = ""))
    }

    private fun String.readLanguage() = File(DEFAULT_FILE_GROUP + "language/$this.lang").readLines().forEach { s ->
        s.split("=", limit = 2).let { v ->
            registeredTexts.find {
                it.containerName == v[0]
            }?.textMap?.set(this, v.getOrElse(1) { "" })
        }
    }

}