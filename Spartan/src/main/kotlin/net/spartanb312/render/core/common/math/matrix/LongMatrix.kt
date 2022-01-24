package net.spartanb312.render.core.common.math.matrix

class LongMatrix(
    columns: Int,
    rows: Int,
    init: (Int, Int) -> Long = { _: Int, _: Int -> 0L }
) : Matrix<Long>(columns, rows, init) {

    override var matrix = Array(rows) { y -> Array(columns) { x -> init.invoke(x, y) } }

    override fun get(x: Int, y: Int): Long = matrix[y - 1][x - 1]

    override fun getPlussed(x: Int, y: Int, addend: Long): Long = matrix[y - 1][x - 1] + addend

    override fun getReduced(x: Int, y: Int, subtraction: Long): Long = matrix[y - 1][x - 1] - subtraction

    override fun getMultiplied(x: Int, y: Int, multiplier: Long): Long = matrix[y - 1][x - 1] * multiplier

    override fun getDivided(x: Int, y: Int, divisor: Long): Long = matrix[y - 1][x - 1] / divisor

    override fun set(x: Int, y: Int, number: Long): Matrix<Long> {
        matrix[y - 1][x - 1] = number
        return this
    }

    override fun setZero(x: Int, y: Int): Matrix<Long> {
        matrix[y - 1][x - 1] = 0L
        return this
    }

    override fun plus(x: Int, y: Int, addend: Long): Matrix<Long> {
        matrix[y - 1][x - 1] += addend
        return this
    }

    override fun reduce(x: Int, y: Int, subtraction: Long): Matrix<Long> {
        matrix[y - 1][x - 1] -= subtraction
        return this
    }

    override fun multiply(x: Int, y: Int, multiplier: Long): Matrix<Long> {
        matrix[y - 1][x - 1] *= multiplier
        return this
    }

    override fun divide(x: Int, y: Int, divisor: Long): Matrix<Long> {
        matrix[y - 1][x - 1] /= divisor
        return this
    }

    override fun transpose(): Matrix<Long> = this.apply {
        val targetRows = columns
        this.columns = rows
        this.rows = targetRows
        this.matrix = LongMatrix(columns, rows) { initX: Int, initY: Int ->
            matrix[initX][initY]
        }.matrix
    }

}