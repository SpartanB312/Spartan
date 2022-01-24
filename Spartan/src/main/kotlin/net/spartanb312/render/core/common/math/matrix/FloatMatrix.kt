package net.spartanb312.render.core.common.math.matrix

class FloatMatrix(
    columns: Int,
    rows: Int,
    init: (Int, Int) -> Float = { _: Int, _: Int -> 0F }
) : Matrix<Float>(columns, rows, init) {

    override var matrix = Array(rows) { y -> Array(columns) { x -> init.invoke(x, y) } }

    override fun get(x: Int, y: Int): Float = matrix[y - 1][x - 1]

    override fun getPlussed(x: Int, y: Int, addend: Float): Float = matrix[y - 1][x - 1] + addend

    override fun getReduced(x: Int, y: Int, subtraction: Float): Float = matrix[y - 1][x - 1] - subtraction

    override fun getMultiplied(x: Int, y: Int, multiplier: Float): Float = matrix[y - 1][x - 1] * multiplier

    override fun getDivided(x: Int, y: Int, divisor: Float): Float = matrix[y - 1][x - 1] / divisor

    override fun set(x: Int, y: Int, number: Float): Matrix<Float> {
        matrix[y - 1][x - 1] = number
        return this
    }

    override fun setZero(x: Int, y: Int): Matrix<Float> {
        matrix[y - 1][x - 1] = 0F
        return this
    }

    override fun plus(x: Int, y: Int, addend: Float): Matrix<Float> {
        matrix[y - 1][x - 1] += addend
        return this
    }

    override fun reduce(x: Int, y: Int, subtraction: Float): Matrix<Float> {
        matrix[y - 1][x - 1] -= subtraction
        return this
    }

    override fun multiply(x: Int, y: Int, multiplier: Float): Matrix<Float> {
        matrix[y - 1][x - 1] *= multiplier
        return this
    }

    override fun divide(x: Int, y: Int, divisor: Float): Matrix<Float> {
        matrix[y - 1][x - 1] /= divisor
        return this
    }

    override fun transpose(): Matrix<Float> = this.apply {
        val targetRows = columns
        this.columns = rows
        this.rows = targetRows
        this.matrix = FloatMatrix(columns, rows) { initX: Int, initY: Int ->
            matrix[initX][initY]
        }.matrix
    }

}