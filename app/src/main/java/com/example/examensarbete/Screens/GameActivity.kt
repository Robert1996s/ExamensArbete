package com.example.examensarbete.Screens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.examensarbete.DataClasses.Question
import com.example.examensarbete.GlobalVariables.CurrentQuestions
import com.example.examensarbete.R
import com.example.examensarbete.ViewModels.GameViewModel

class GameActivity : AppCompatActivity() {

    private val playerTrueAnswer = 0
    private val playerFalseAnswer = 1
    private var questionCount = 0
    private var playerScore = 0
    private var correctAnswers = mutableListOf<Int>()
    lateinit var viewModel:GameViewModel
    private var questionList = mutableListOf<String?>()
    private var objQuestionList = mutableListOf<Question>()
    private var questionIndex = 0

    private var test = MutableLiveData<MutableList<Question>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val categoryText = findViewById<TextView>(R.id.chosen_category_text)

        val questionText = findViewById<TextView>(R.id.question_text)
        val trueAnswer = findViewById<Button>(R.id.true_button)
        val falseAnswer = findViewById<Button>(R.id.false_button)
        val exitBtn = findViewById<Button>(R.id.exit_game_button)

        //Setting the category text to the chosen one from the previous activity
        val userCat = intent.getStringExtra("category").toString()
        categoryText.text = userCat

        //TODO göra om poäng här iställer för model


        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        viewModel.getQuestions(userCat)
        viewModel.getObjQuestionList()


        /*Thread(Runnable {
            Thread.sleep(500)
            correctAnswers = viewModel.correctAnswers
            //questionList = viewModel.questionTextList
            println("!!! GAME ANSWERS : $correctAnswers")
        }).start() */



        fun setInList(list: List<Question>) {
            //TODO LISTA SOM LOOPAR IGENOM IT LISTA OCH SÄTTER
            for (question in list) {
                objQuestionList.add(question)
            }
            println("!!!LIST FOR LOOP $objQuestionList")
        }

        viewModel.getObjQuestionList().observe(this, Observer {
            setInList(it)
            questionText.text = objQuestionList[questionIndex].text
            println("!!!OBJ list ACTIVITY : ${objQuestionList}")
        })


        trueAnswer.setBackgroundColor(Color.parseColor("#0CF72F"))
        falseAnswer.setBackgroundColor(Color.parseColor("#cf0000"))
        exitBtn.setBackgroundColor(Color.parseColor("#cf0000"))

        trueAnswer.setOnClickListener {
            checkAnswer(playerTrueAnswer)
            questionText.text = objQuestionList[questionIndex].text
            println("!!!count: $questionCount")
        }

        falseAnswer.setOnClickListener {
            checkAnswer(playerFalseAnswer)
        }

        exitBtn.setOnClickListener {
            navigateHome()
        }
    }

    private fun navigateHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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
        val playerScoreText = findViewById<TextView>(R.id.player_score)
        playerScoreText.text = viewModel.player_score.toString()
        viewModel.addScore()
    }

    private fun loadNextQuestion () {
        questionIndex++
        println("!!!count: $questionCount")
        println("!!! Next Question")
    }
}