package net.spartanb312.render.core.common.math.matrix

fun main() {
    DoubleMatrix(
        rows = 3,
        columns = 4,
        init = { x: Int, y: Int ->
            (x + y).toDouble()
        }
    ).print()
        .addMultipliedRow(1, 2, 1.0)
        .print()
        .transpose()
        .print()
        .plusRow(4, 10.0)
        .print()
        .transpose()
        .print()

}

/**
 * Do you like Higher Algebra?
 */
abstract class Matrix<T : Number>(
    var columns: Int,
    var rows: Int,
    val init: (Int, Int) -> T
) {

    abstract var matrix: Array<Array<T>>

    //Setter
    abstract fun set(x: Int, y: Int, number: T): Matrix<T>
    abstract fun setZero(x: Int, y: Int): Matrix<T>

    //Getter
    abstract fun get(x: Int, y: Int): T
    abstract fun getPlussed(x: Int, y: Int, addend: T): T
    abstract fun getReduced(x: Int, y: Int, subtraction: T): T
    abstract fun getMultiplied(x: Int, y: Int, multiplier: T): T
    abstract fun getDivided(x: Int, y: Int, divisor: T): T

    //Four arithmetic
    @HomomorphicTransformation
    abstract fun plus(x: Int, y: Int, addend: T): Matrix<T>

    @HomomorphicTransformation
    abstract fun reduce(x: Int, y: Int, subtraction: T): Matrix<T>

    @HomomorphicTransformation
    abstract fun multiply(x: Int, y: Int, multiplier: T): Matrix<T>

    @HomomorphicTransformation
    abstract fun divide(x: Int, y: Int, divisor: T): Matrix<T>

    //Other
    @NonHomomorphicTransformation
    abstract fun transpose(): Matrix<T>

    /**
     * Set a number
     */
    @HomomorphicTransformation
    fun setAll(number: T): Matrix<T> {
        for (y in 1..rows) {
            for (x in 1..columns) {
                set(x, y, number)
            }
        }
        return this
    }

    @HomomorphicTransformation
    fun setRow(y: Int, number: T): Matrix<T> {
        for (x in 1..columns) {
            set(x, y, number)
        }
        return this
    }

    @HomomorphicTransformation
    fun setColumn(x: Int, number: T): Matrix<T> {
        for (y in 1..rows) {
            set(x, y, number)
        }
        return this
    }

    /**
     * Set to zero
     */
    @HomomorphicTransformation
    fun setZeroAll(): Matrix<T> {
        for (y in 1..rows) {
            for (x in 1..columns) {
                setZero(x, y)
            }
        }
        return this
    }

    @HomomorphicTransformation
    fun setZeroRow(y: Int): Matrix<T> {
        for (x in 1..columns) {
            setZero(x, y)
        }
        return this
    }

    @HomomorphicTransformation
    fun setZeroColumn(x: Int): Matrix<T> {
        for (y in 1..rows) {
            setZero(x, y)
        }
        return this
    }

    /**
     * Plus a number
     */
    @HomomorphicTransformation
    fun plusAll(addend: T): Matrix<T> {
        for (y in 1..rows) {
            for (x in 1..columns) {
                plus(x, y, addend)
            }
        }
        return this
    }

    @HomomorphicTransformation
    fun plusRow(y: Int, addend: T): Matrix<T> {
        for (x in 1..columns) {
            plus(x, y, addend)
        }
        return this
    }

    @HomomorphicTransformation
    fun plusColumn(x: Int, addend: T): Matrix<T> {
        for (y in 1..rows) {
            plus(x, y, addend)
        }
        return this
    }

    /**
     * Reduce a number
     */
    @HomomorphicTransformation
    fun reduceAll(subtraction: T): Matrix<T> {
        for (y in 1..rows) {
            for (x in 1..columns) {
                reduce(x, y, subtraction)
            }
        }
        return this
    }

    @HomomorphicTransformation
    fun reduceRow(y: Int, subtraction: T): Matrix<T> {
        for (x in 1..columns) {
            reduce(x, y, subtraction)
        }
        return this
    }

    @HomomorphicTransformation
    fun reduceColumn(x: Int, subtraction: T): Matrix<T> {
        for (y in 1..rows) {
            reduce(x, y, subtraction)
        }
        return this
    }

    /**
     * Multiply a number
     */
    @ElementaryTransformation
    fun multiplyAll(multiplier: T): Matrix<T> {
        for (y in 1..rows) {
            for (x in 1..columns) {
                multiply(x, y, multiplier)
            }
        }
        return this
    }

    @ElementaryTransformation
    fun multiplyRow(y: Int, multiplier: T): Matrix<T> {
        for (x in 1..columns) {
            multiply(x, y, multiplier)
        }
        return this
    }

    @ElementaryTransformation
    fun multiplyColumn(x: Int, multiplier: T): Matrix<T> {
        for (y in 1..rows) {
            multiply(x, y, multiplier)
        }
        return this
    }

    /**
     * Divide a number
     */
    @ElementaryTransformation
    fun divideAll(divisor: T): Matrix<T> {
        for (y in 1..rows) {
            for (x in 1..columns) {
                divide(x, y, divisor)
            }
        }
        return this
    }

    @ElementaryTransformation
    fun divideRow(y: Int, divisor: T): Matrix<T> {
        for (x in 1..columns) {
            divide(x, y, divisor)
        }
        return this
    }

    @ElementaryTransformation
    fun divideColumn(x: Int, divisor: T): Matrix<T> {
        for (y in 1..rows) {
            divide(x, y, divisor)
        }
        return this
    }

    /**
     * Row transformation
     */
    @ElementaryTransformation
    fun addMultipliedRow(sourceY: Int, targetY: Int, multiplier: T): Matrix<T> {
        for (x in 1..columns) {
            plus(x, targetY, getMultiplied(x, sourceY, multiplier))
        }
        return this
    }

    @ElementaryTransformation
    fun addDividedRow(sourceY: Int, targetY: Int, divisor: T): Matrix<T> {
        for (x in 1..columns) {
            plus(x, targetY, getDivided(x, sourceY, divisor))
        }
        return this
    }

    @ElementaryTransformation
    fun reduceMultipliedRow(sourceY: Int, targetY: Int, multiplier: T): Matrix<T> {
        for (x in 1..columns) {
            reduce(x, targetY, getMultiplied(x, sourceY, multiplier))
        }
        return this
    }

    @ElementaryTransformation
    fun reduceDividedRow(sourceY: Int, targetY: Int, divisor: T): Matrix<T> {
        for (x in 1..columns) {
            reduce(x, targetY, getDivided(x, sourceY, divisor))
        }
        return this
    }

    /**
     * Column transformation
     */
    @ElementaryTransformation
    fun addMultipliedColumn(sourceX: Int, targetX: Int, multiplier: T): Matrix<T> {
        for (y in 1..rows) {
            plus(targetX, y, getMultiplied(sourceX, y, multiplier))
        }
        return this
    }

    @ElementaryTransformation
    fun addDividedColumn(sourceX: Int, targetX: Int, divisor: T): Matrix<T> {
        for (y in 1..rows) {
            plus(targetX, y, getDivided(sourceX, y, divisor))
        }
        return this
    }

    @ElementaryTransformation
    fun reduceMultipliedColumn(sourceX: Int, targetX: Int, multiplier: T): Matrix<T> {
        for (y in 1..rows) {
            reduce(targetX, y, getMultiplied(sourceX, y, multiplier))
        }
        return this
    }

    @ElementaryTransformation
    fun reduceDividedColumn(sourceX: Int, targetX: Int, divisor: T): Matrix<T> {
        for (y in 1..rows) {
            reduce(targetX, y, getDivided(sourceX, y, divisor))
        }
        return this
    }

    @DslMarker
    annotation class ElementaryTransformation

    @DslMarker
    annotation class NonHomomorphicTransformation

    @DslMarker
    annotation class HomomorphicTransformation

    fun print(): Matrix<T> {
        for (y in 1..rows) {
            for (x in 1..columns) {
                print("${get(x, y)}, ")
            }
            println("")
        }
        println("")
        return this
    }

}