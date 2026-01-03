package com.example.snowwhiteproject

import kotlin.random.Random

/**
 * Manages the game board (matrix) and the game rules.
 *
 * Cell values:
 * 0 = EMPTY
 * 1 = POISON_APPLE (hit = lose life)
 * 2 = COIN (collect = score)
 */
class GameManager(
    val rows: Int,
    val cols: Int
) {
    companion object {
        const val EMPTY = 0
        const val POISON_APPLE = 1
        const val COIN = 2
    }

    private val matrix = Array(rows) { IntArray(cols) { EMPTY } }

    var distance: Int = 0
        private set

    var score: Int = 0
        private set

    fun clearAll() {
        for (r in 0 until rows) {
            for (c in 0 until cols) {
                matrix[r][c] = EMPTY
            }
        }
        distance = 0
        score = 0
    }

    fun getCell(row: Int, col: Int): Int = matrix[row][col]

    /**
     * One "tick" of the game:
     * - move everything down
     * - maybe spawn an object at top row
     * - increase distance
     */
    fun step() {
        moveDown()
        spawnObject()
        distance += 1
    }

    private fun moveDown() {
        for (r in rows - 1 downTo 1) {
            for (c in 0 until cols) {
                matrix[r][c] = matrix[r - 1][c]
            }
        }
        for (c in 0 until cols) {
            matrix[0][c] = EMPTY
        }
    }

    private fun spawnObject() {
        // Simple spawn logic:
        // 40% nothing, 45% apple, 15% coin
        val roll = Random.nextInt(100)
        val col = Random.nextInt(cols)

        when {
            roll < 40 -> Unit
            roll < 85 -> matrix[0][col] = POISON_APPLE
            else -> matrix[0][col] = COIN
        }
    }

    /**
     * Checks the bottom row at the player's column and resolves collision (if any).
     * Returns the collided object type, or EMPTY if no collision.
     */
    fun resolveCollisionAt(playerCol: Int): Int {
        val cell = matrix[rows - 1][playerCol]
        if (cell != EMPTY) {
            // clear the cell after collision/collection
            matrix[rows - 1][playerCol] = EMPTY

            if (cell == COIN) {
                score += 50
            }
        }
        return cell
    }
}
