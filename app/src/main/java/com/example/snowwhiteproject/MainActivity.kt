package com.example.snowwhiteproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val ROWS = 7
    private val COLS = 3
    private val DELAY = 700L

    private lateinit var matrixViews: Array<Array<View>>
    private val board = Array(ROWS) { IntArray(COLS) }

    private lateinit var player: ImageView
    private lateinit var hearts: Array<ImageView>
    private var playerCol = 1
    private var lives = 3

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var gameRunnable: Runnable
    private var gameRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMatrix()
        initViews()
        initButtons()
        startGame()
    }

    private fun initViews() {
        player = findViewById(R.id.main_IMG_player)
        hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )
    }

    private fun initMatrix() {
        matrixViews = Array(ROWS) { r ->
            Array(COLS) { c ->
                val id = resources.getIdentifier("mat_${r}_${c}", "id", packageName)
                findViewById(id)
            }
        }
    }

    private fun initButtons() {
        findViewById<ImageButton>(R.id.main_BTN_left).setOnClickListener {
            if (playerCol > 0) playerCol--
            updatePlayerPosition()
        }

        findViewById<ImageButton>(R.id.main_BTN_right).setOnClickListener {
            if (playerCol < COLS - 1) playerCol++
            updatePlayerPosition()
        }
    }

    private fun startGame() {
        gameRunning = true
        gameRunnable = object : Runnable {
            override fun run() {
                if (!gameRunning) return
                gameStep()
                handler.postDelayed(this, DELAY)
            }
        }
        handler.postDelayed(gameRunnable, DELAY)
    }

    private fun stopGame() {
        gameRunning = false
        handler.removeCallbacks(gameRunnable)
    }

    private fun gameStep() {
        moveDown()
        spawnApple()
        checkCollision()
        draw()
    }

    private fun moveDown() {
        for (r in ROWS - 1 downTo 1) {
            for (c in 0 until COLS) {
                board[r][c] = board[r - 1][c]
            }
        }
        for (c in 0 until COLS) board[0][c] = 0
    }

    private fun spawnApple() {
        if (Random.nextBoolean()) {
            board[0][Random.nextInt(COLS)] = 1
        }
    }

    private fun checkCollision() {
        if (board[ROWS - 1][playerCol] == 1) {
            board[ROWS - 1][playerCol] = 0
            loseLife()
        }
    }

    private fun loseLife() {
        crashFeedback()
        lives--
        if (lives >= 0) hearts[lives].visibility = View.INVISIBLE

        if (lives == 0) {
            stopGame()
            startActivity(Intent(this, ScoreActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        if (lives == 0) {
            resetGame()
            startGame()
        }
    }

    private fun resetGame() {
        lives = 3
        hearts.forEach { it.visibility = View.VISIBLE }

        for (r in 0 until ROWS)
            for (c in 0 until COLS)
                board[r][c] = 0

        playerCol = 1
        updatePlayerPosition()
    }

    private fun draw() {
        for (r in 0 until ROWS) {
            for (c in 0 until COLS) {
                if (board[r][c] == 1)
                    matrixViews[r][c].setBackgroundResource(R.drawable.apple)
                else
                    matrixViews[r][c].background = null
            }
        }
    }

    private fun updatePlayerPosition() {
        val parentWidth = (player.parent as View).width
        val laneWidth = parentWidth / COLS
        player.x = laneWidth * playerCol + laneWidth / 2f - player.width / 2f
    }

    private fun crashFeedback() {
        Toast.makeText(this, "Ouch!", Toast.LENGTH_SHORT).show()
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    300,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopGame()
    }
}
