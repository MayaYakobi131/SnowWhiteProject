package com.example.snowwhiteproject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val btnContinue = findViewById<Button>(R.id.score_BTN_continue)
        btnContinue.setOnClickListener {
            finish()
        }
    }
}
