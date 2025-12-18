package com.example.snowwhiteproject

import kotlin.random.Random

class GameManager(
    private val rows: Int,
    private val cols: Int
) {

    private val matrix = Array(rows) { IntArray(cols) { 0 } }

    fun moveDown() {
        for (r in rows - 1 downTo 1) {
            for (c in 0 until cols) {
                matrix[r][c] = matrix[r - 1][c]
            }
        }

        for (c in 0 until cols) {
            matrix[0][c] = 0
        }
    }

    fun spawnApple() {
        val col = Random.nextInt(cols)
        matrix[0][col] = 1
    }

    fun hasApple(row: Int, col: Int): Boolean {
        return matrix[row][col] == 1
    }

    fun clearCell(row: Int, col: Int) {
        matrix[row][col] = 0
    }

    fun clearAll() {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                matrix[r][c] = 0
            }
        }
    }

    fun getCell(row: Int, col: Int): Int {
        return matrix[row][col]
    }
}
