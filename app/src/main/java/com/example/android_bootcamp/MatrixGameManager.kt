package com.example.android_bootcamp

class MatrixGameManager(private val size: Int) {

    private val matrix = Array(size) { Array(size) { Type.UNCHECKED } }

    fun updateCell(id: Int, type: Type): Boolean {
        val row = id / size
        val col = id % size
        if (matrix[row][col] == Type.UNCHECKED) {
            matrix[row][col] = type
            return checkWin(row, col, type)
        }
        return false
    }

    fun checkDraw():Boolean{
        var isDraw = true
        matrix.flatten().forEachIndexed { _, value ->
            if (value == Type.UNCHECKED) isDraw = false
        }
        return isDraw
    }

    private fun checkWin(row: Int, col: Int, type: Type): Boolean {
        return (checkRow(row, type) || checkColumn(col, type) || checkDiagonals(type))
    }

    private fun checkRow(row: Int, player: Type): Boolean {
        for (col in 0 until size) {
            if (matrix[row][col] != player) return false
        }
        return true
    }

    private fun checkColumn(col: Int, player: Type): Boolean {
        for (row in 0 until size) {
            if (matrix[row][col] != player) return false
        }
        return true
    }

    private fun checkDiagonals(type: Type): Boolean {
        var firstDiagonal = true
        var secondDiagonal = true
        for (i in 0 until size) {
            if (matrix[i][i] != type) firstDiagonal = false
            if (matrix[i][size - i - 1] != type) secondDiagonal = false
        }
        return firstDiagonal || secondDiagonal
    }

    fun getMatrix(): Array<Array<Type>> {
        return matrix
    }
}

