package net.spartanb312.render.core.common

import net.spartanb312.render.core.common.collections.list
import java.io.File
import java.net.URL
import java.util.function.Predicate
import java.util.jar.JarInputStream

object ClassUtils {

    inline fun <reified T> Set<Class<*>>.getAnnotatedClasses(
        startWith: Array<String> = arrayOf(
            "java.",
            "sun",
            "org.lwjgl",
            "org.apache.logging",
            "net.minecraft",
            "mixin"
        ),
        crossinline block: (String) -> Boolean = { true }
    ): List<Class<*>> where T : Annotation {
        return list {
            this@getAnnotatedClasses.filter {
                startWith.none { ex -> it.name.startsWith(ex) } && block(it.name) && it.isAnnotationPresent(T::class.java)
            }.forEach {
                yield(it)
            }
        }
    }

    inline fun <reified T> String.getAnnotatedClasses(
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader,
        startWith: Array<String> = arrayOf(
            "java.",
            "sun",
            "org.lwjgl",
            "org.apache.logging",
            "net.minecraft",
            "mixin"
        ),
        crossinline block: (String) -> Boolean = { true }
    ): List<Class<*>> where T : Annotation {
        return list {
            findClasses(this@getAnnotatedClasses, classLoader) {
                startWith.none { ex -> it.startsWith(ex) } && block(it)
            }.filter {
                it.isAnnotationPresent(T::class.java)
            }.forEach {
                yield(it)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> String.findTypedClasses(crossinline predicate: String.() -> Boolean = { true }): List<Class<T>> {
        return list {
            findClasses(this@findTypedClasses) { it.predicate() }
                .forEach {
                    if (T::class.java.isAssignableFrom(it)) yield(it as Class<T>)
                }
        }
    }

    fun findClasses(
        packageName: String,
        classLoader: ClassLoader = Thread.currentThread().contextClassLoader,
        predicate: Predicate<String> = Predicate { true }
    ): List<Class<*>> {

        val packagePath = packageName.replace('.', '/')

        val classes = ArrayList<Class<*>>()

        val root: URL? = classLoader.getResource(packagePath)
        if (root != null) {
            if (root.toString().startsWith("jar")) {
                val path = root.path
                val url = URL(path.substring(0, path.lastIndexOf('!')))
                findClassesInJar(classLoader, File(url.file), packagePath, predicate, classes)
            }
        } else {
            classLoader.getResources(packagePath)?.let {
                for (url in it) {
                    findClasses(classLoader, File(url.file), packageName, predicate, classes)
                }
            }
        }

        return classes
    }

    private fun findClasses(
        classLoader: ClassLoader,
        directory: File,
        packageName: String,
        predicate: Predicate<String> = Predicate { true },
        list: MutableList<Class<*>>
    ): List<Class<*>> {
        if (!directory.exists()) return list

        val packagePath = packageName.replace('.', File.separatorChar)

        directory.walk()
            .filter { it.isFile }
            .filter { it.extension == "class" }
            .map { it.path.substringAfter(packagePath) }
            .map { it.replace(File.separatorChar, '.') }
            .map { it.substring(0, it.length - 6) }
            .map { "$packageName$it" }
            .filter { predicate.test(it) }
            .map { Class.forName(it, false, classLoader) }
            .toCollection(list)

        return list
    }

    private fun findClassesInJar(
        classLoader: ClassLoader,
        jarFile: File,
        packageName: String,
        predicate: Predicate<String> = Predicate { true },
        list: MutableList<Class<*>>
    ) {
        JarInputStream(jarFile.inputStream()).use {
            var entry = it.nextJarEntry
            while (entry != null) {
                if (!entry.isDirectory) {
                    val name = entry.name

                    if (name.startsWith(packageName) && name.endsWith(".class")) {
                        val className = name
                            .replace('/', '.')
                            .substring(0, name.length - 6)

                        if (predicate.test(className)) {
                            list.add(Class.forName(className, false, classLoader))
                        }
                    }
                }

                entry = it.nextJarEntry
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    inline val <T> Class<out T>.instance: T?
        get() = try {
            this.getDeclaredField("INSTANCE")[null] as T?
        } catch (ignore: Exception) {
            null
        }

}
