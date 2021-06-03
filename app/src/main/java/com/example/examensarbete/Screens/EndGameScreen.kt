package com.example.examensarbete.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.examensarbete.R

class EndGameScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game_screen)

        val endPlayerScore = findViewById<TextView>(R.id.end_score)
        val continueBtn = findViewById<Button>(R.id.continue_button)


        val playerScore = intent.getStringExtra("score")

        endPlayerScore.text = playerScore

        continueBtn.setOnClickListener {
            navigateHome()
        }
    }

    //Navigate to main
    private fun navigateHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}