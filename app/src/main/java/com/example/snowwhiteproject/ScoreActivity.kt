package com.example.snowwhiteproject

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SCORE = "extra_score"
        const val EXTRA_DISTANCE = "extra_distance"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        val distance = intent.getIntExtra(EXTRA_DISTANCE, 0)

        findViewById<TextView>(R.id.score_LBL_details).text =
            "Score: $score\nDistance: $distance"

        findViewById<Button>(R.id.score_BTN_continue).setOnClickListener {
            finish()
        }
    }
}
