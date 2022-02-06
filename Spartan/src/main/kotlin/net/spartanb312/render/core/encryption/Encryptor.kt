package net.spartanb312.render.core.encryption

interface Encryptor {
    fun encode(input: String, key: String): String
    fun decode(input: String, key: String): String
    fun String.processKey(): String
}