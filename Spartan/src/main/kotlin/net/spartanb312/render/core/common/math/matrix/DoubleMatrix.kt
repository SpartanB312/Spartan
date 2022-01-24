package net.spartanb312.render.core.common.math.matrix

class DoubleMatrix(
    columns: Int,
    rows: Int,
    init: (Int, Int) -> Double = { _: Int, _: Int -> 0.0 }
) : Matrix<Double>(columns, rows, init) {

    override var matrix = Array(rows) { y -> Array(columns) { x -> init.invoke(x, y) } }

    override fun get(x: Int, y: Int): Double = matrix[y - 1][x - 1]

    override fun getPlussed(x: Int, y: Int, addend: Double): Double = matrix[y - 1][x - 1] + addend

    override fun getReduced(x: Int, y: Int, subtraction: Double): Double = matrix[y - 1][x - 1] - subtraction

    override fun getMultiplied(x: Int, y: Int, multiplier: Double): Double = matrix[y - 1][x - 1] * multiplier

    override fun getDivided(x: Int, y: Int, divisor: Double): Double = matrix[y - 1][x - 1] / divisor

    override fun set(x: Int, y: Int, number: Double): Matrix<Double> {
        matrix[y - 1][x - 1] = number
        return this
    }

    override fun setZero(x: Int, y: Int): Matrix<Double> {
        matrix[y - 1][x - 1] = 0.0
        return this
    }

    override fun plus(x: Int, y: Int, addend: Double): Matrix<Double> {
        matrix[y - 1][x - 1] += addend
        return this
    }

    override fun reduce(x: Int, y: Int, subtraction: Double): Matrix<Double> {
        matrix[y - 1][x - 1] -= subtraction
        return this
    }

    override fun multiply(x: Int, y: Int, multiplier: Double): Matrix<Double> {
        matrix[y - 1][x - 1] *= multiplier
        return this
    }

    override fun divide(x: Int, y: Int, divisor: Double): Matrix<Double> {
        matrix[y - 1][x - 1] /= divisor
        return this
    }

    override fun transpose(): Matrix<Double> = this.apply {
        val targetRows = columns
        this.columns = rows
        this.rows = targetRows
        this.matrix = DoubleMatrix(columns, rows) { initX: Int, initY: Int ->
            matrix[initX][initY]
        }.matrix
    }

}