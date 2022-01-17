package net.spartanb312.render.core.reflect

import java.lang.reflect.Modifier

inline fun <reified T> findStaticInstance(): T? =
    Class.forName(T::class.java.name)?.declaredFields?.asSequence()
        ?.filter { field ->
            (field.type == T::class.java
                    || field.type.isAssignableFrom(T::class.java))
                    && Modifier.isStatic(field.modifiers)
        }?.firstOrNull()?.get(null) as T?

fun <T> findStaticInstance(packageName: String): T? =
    Class.forName(packageName)?.declaredFields?.asSequence()
        ?.filter { field ->
            field.type.name == packageName && Modifier.isStatic(field.modifiers)
        }?.firstOrNull()?.get(null).cast(packageName)

inline fun <reified T> findKotlinObjectInstance(): T? =
    Class.forName(T::class.java.name)?.declaredFields?.asSequence()
        ?.filter { field -> field.type == T::class.java && Modifier.isStatic(field.modifiers) }
        ?.firstOrNull { field -> field.name == "INSTANCE" }?.get(null) as T?

fun <T> findKotlinObjectInstance(packageName: String): T? =
    Class.forName(packageName)?.declaredFields?.asSequence()
        ?.filter { field -> field.type.name == packageName && Modifier.isStatic(field.modifiers) }
        ?.firstOrNull { field -> field.name == "INSTANCE" }?.get(null).cast(packageName)

inline fun <reified T> getOrCreateKotlinInstance(): T? =
    (findKotlinObjectInstance<T>() ?: Class.forName(T::class.java.name)?.newInstance() as T?)

fun <T> getOrCreateKotlinInstance(packageName: String): T? =
    (findKotlinObjectInstance(packageName) ?: Class.forName(packageName)?.newInstance()).cast(packageName)