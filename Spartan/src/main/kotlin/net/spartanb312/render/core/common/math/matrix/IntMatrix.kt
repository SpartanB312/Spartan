package net.spartanb312.render.core.common.math.matrix

class IntMatrix(
    columns: Int,
    rows: Int,
    init: (Int, Int) -> Int = { _: Int, _: Int -> 0 }
) : Matrix<Int>(columns, rows, init) {

    override var matrix = Array(rows) { y -> Array(columns) { x -> init.invoke(x, y) } }

    override fun get(x: Int, y: Int): Int = matrix[y - 1][x - 1]

    override fun getPlussed(x: Int, y: Int, addend: Int): Int = matrix[y - 1][x - 1] + addend

    override fun getReduced(x: Int, y: Int, subtraction: Int): Int = matrix[y - 1][x - 1] - subtraction

    override fun getMultiplied(x: Int, y: Int, multiplier: Int): Int = matrix[y - 1][x - 1] * multiplier

    override fun getDivided(x: Int, y: Int, divisor: Int): Int = matrix[y - 1][x - 1] / divisor

    override fun set(x: Int, y: Int, number: Int): Matrix<Int> {
        matrix[y - 1][x - 1] = number
        return this
    }

    override fun setZero(x: Int, y: Int): Matrix<Int> {
        matrix[y - 1][x - 1] = 0
        return this
    }

    override fun plus(x: Int, y: Int, addend: Int): Matrix<Int> {
        matrix[y - 1][x - 1] += addend
        return this
    }

    override fun reduce(x: Int, y: Int, subtraction: Int): Matrix<Int> {
        matrix[y - 1][x - 1] -= subtraction
        return this
    }

    override fun multiply(x: Int, y: Int, multiplier: Int): Matrix<Int> {
        matrix[y - 1][x - 1] *= multiplier
        return this
    }

    override fun divide(x: Int, y: Int, divisor: Int): Matrix<Int> {
        matrix[y - 1][x - 1] /= divisor
        return this
    }

    override fun transpose(): Matrix<Int> = this.apply {
        val targetRows = columns
        this.columns = rows
        this.rows = targetRows
        this.matrix = IntMatrix(columns, rows) { initX: Int, initY: Int ->
            matrix[initX][initY]
        }.matrix
    }

}