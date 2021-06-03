package com.example.examensarbete.Screens

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.examensarbete.DataClasses.Category
import com.example.examensarbete.Firebase.GetCategories
import com.example.examensarbete.Firebase.GetQuestions
import com.example.examensarbete.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class Categories : AppCompatActivity() {

    private var categoryList = mutableListOf<String>()
    private var categoryUidList = mutableListOf<String>()
    lateinit var db: FirebaseFirestore
    private var chosenCat = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        db = FirebaseFirestore.getInstance()
        getCategories()

        val backBtn = findViewById<Button>(R.id.back_button)

        backBtn.setBackgroundColor(Color.parseColor("#cf0000"))

        backBtn.setOnClickListener {
            goBack()
        }
    } // On create

    private fun setCategories(listCat: MutableList<String>) {

        val category1 = findViewById<Button>(R.id.category1_button)
        val category2 = findViewById<Button>(R.id.category2_button)
        val category3 = findViewById<Button>(R.id.category3_button)


        //Setting button colors
        category1.setBackgroundColor(Color.parseColor("#1cc5dc"))
        category2.setBackgroundColor(Color.parseColor("#1cc5dc"))
        category3.setBackgroundColor(Color.parseColor("#1cc5dc"))


        //Setting the values when pressed
        category1.setOnClickListener {
            chosenCat = listCat[0]
            val catUid = categoryUidList[0]
            println("!!! category chosen: $chosenCat")
            GetQuestions().getQuestions(chosenCat)
            startGame()
        }

        category2.setOnClickListener {
            chosenCat = listCat[1]
            val catUid = categoryUidList[1]
            println("!!! category chosen: $chosenCat")
            GetQuestions().getQuestions(chosenCat)
            startGame()
        }
        category3.setOnClickListener {
            chosenCat = listCat[2]
            val catUid = categoryUidList[2]
            println("!!! category chosen: $chosenCat")
            GetQuestions().getQuestions(chosenCat)
            startGame()
        }
        category1.text = listCat[0]
        category2.text = listCat[1]
        category3.text = listCat[2]
    }

    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun getCategories() {
        //TODO use another class to get the cats!
        db.collection("Categories").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val cat = document.toObject(Category::class.java)
                    categoryList.add(cat.categoryName!!)
                    categoryUidList.add(document.id)
                    println("!!! ${cat.categoryName}")
                }
                setCategories(categoryList)
            }
    }

    private fun startGame() {
        toGameScreen()
    }

    private fun toGameScreen() {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("category", chosenCat)
        startActivity(intent)
    }
}