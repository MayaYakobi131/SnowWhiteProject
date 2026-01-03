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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val ROWS = 9
    private val COLS = 5

    // בשלב הבא זה יקבע מה-Menu (Slow/Fast)
    private var delayMs: Long = 700L

    private lateinit var matrixViews: Array<Array<ImageView>>
    private lateinit var player: ImageView
    private lateinit var hearts: Array<ImageView>
    private lateinit var lblDistance: TextView
    private lateinit var lblScore: TextView

    private var playerCol = 2
    private var lives = 3

    private lateinit var gameManager: GameManager

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var gameRunnable: Runnable
    private var gameRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameManager = GameManager(rows = ROWS, cols = COLS)

        initMatrix()
        initViews()
        initButtons()

        resetGame()
        startGame()
    }

    private fun initViews() {
        player = findViewById(R.id.main_IMG_player)
        hearts = arrayOf(
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)
        )

        lblDistance = findViewById(R.id.main_LBL_distance)
        lblScore = findViewById(R.id.main_LBL_score)
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
            if (playerCol > 0) {
                playerCol--
                updatePlayerPosition()
            }
        }

        findViewById<ImageButton>(R.id.main_BTN_right).setOnClickListener {
            if (playerCol < COLS - 1) {
                playerCol++
                updatePlayerPosition()
            }
        }
    }

    private fun startGame() {
        gameRunning = true
        gameRunnable = object : Runnable {
            override fun run() {
                if (!gameRunning) return
                gameStep()
                handler.postDelayed(this, delayMs)
            }
        }
        handler.postDelayed(gameRunnable, delayMs)
    }

    private fun stopGame() {
        gameRunning = false
        handler.removeCallbacks(gameRunnable)
    }

    private fun gameStep() {
        gameManager.step()

        val collision = gameManager.resolveCollisionAt(playerCol)
        when (collision) {
            GameManager.POISON_APPLE -> loseLife()
            GameManager.COIN -> {
                // הניקוד עולה ב-GameManager
                // בשלב הסאונדים נוסיף כאן סאונד מטבע
            }
        }

        draw()
        updateHud()
    }

    private fun updateHud() {
        lblDistance.text = "Distance: ${gameManager.distance}"
        lblScore.text = "Score: ${gameManager.score}"
    }

    private fun loseLife() {
        crashFeedback()
        lives--
        if (lives >= 0) {
            hearts[lives].visibility = View.INVISIBLE
        }

        if (lives == 0) {
            stopGame()
            val i = Intent(this, ScoreActivity::class.java)
            i.putExtra(ScoreActivity.EXTRA_SCORE, gameManager.score)
            i.putExtra(ScoreActivity.EXTRA_DISTANCE, gameManager.distance)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        // חוזרים מ-GameOver -> מאפסים ומתחילים מחדש
        if (lives == 0) {
            resetGame()
            startGame()
        }
    }

    private fun resetGame() {
        lives = 3
        hearts.forEach { it.visibility = View.VISIBLE }

        gameManager.clearAll()

        playerCol = 2
        updatePlayerPosition()
        updateHud()
        draw()
    }

    private fun draw() {
        for (r in 0 until ROWS) {
            for (c in 0 until COLS) {
                when (gameManager.getCell(r, c)) {
                    GameManager.POISON_APPLE -> matrixViews[r][c].setImageResource(R.drawable.apple)
                    GameManager.COIN -> matrixViews[r][c].setImageResource(android.R.drawable.star_big_on) // זמני למטבע
                    else -> matrixViews[r][c].setImageDrawable(null)
                }
            }
        }
    }

    private fun updatePlayerPosition() {
        val parentWidth = (player.parent as View).width
        if (parentWidth == 0) {
            player.post { updatePlayerPosition() }
            return
        }
        val laneWidth = parentWidth / COLS.toFloat()
        player.x = laneWidth * playerCol + laneWidth / 2f - player.width / 2f
    }

    private fun crashFeedback() {
        Toast.makeText(this, "Ouch!", Toast.LENGTH_SHORT).show()

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    250,
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
