package com.example.examensarbete.Screens

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.examensarbete.R
import com.example.examensarbete.ViewModels.GameViewModel

class GameActivity : AppCompatActivity() {

    private val playerTrueAnswer = 0
    private val playerFalseAnswer = 1
    private var questionCount = 0
    lateinit var viewModel:GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)



        val categoryText = findViewById<TextView>(R.id.chosen_category_text)
        val playerScoreText = findViewById<TextView>(R.id.player_score)
        val questionText = findViewById<TextView>(R.id.question_text)
        val trueAnswer = findViewById<Button>(R.id.true_button)
        val falseAnswer = findViewById<Button>(R.id.false_button)
        val exitBtn = findViewById<Button>(R.id.exit_game_button)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        playerScoreText.text = viewModel.player_score.toString()

        trueAnswer.setBackgroundColor(Color.parseColor("#0CF72F"))
        falseAnswer.setBackgroundColor(Color.parseColor("#cf0000"))
        exitBtn.setBackgroundColor(Color.parseColor("#cf0000"))

        trueAnswer.setOnClickListener {
            checkAnswer(playerTrueAnswer)
            viewModel.addScore()
            playerScoreText.text = viewModel.player_score.toString()
        }

        falseAnswer.setOnClickListener {
            checkAnswer(playerFalseAnswer)
        }
    }

    private fun checkAnswer(answer: Int) {
        val correct = 0 //Correct question answer
        if (answer == correct) {
            increaseScore()
            loadNextQuestion()
            println("!!! TRUE")
        } else {
            loadNextQuestion()
            println("!!!FLASE")
        }
    }

    private fun increaseScore () {
        //val playerScoreText = findViewById<TextView>(R.id.player_score)
        //playerScoreText.text = viewModel.player_score.toString()
        //viewModel.addScore()

    }
    private fun loadNextQuestion () {
        println("!!! Next Question")
    }
}