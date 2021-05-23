package com.example.examensarbete.Firebase

import com.example.examensarbete.DataClasses.Question
import com.example.examensarbete.GlobalVariables.CurrentQuestions
import com.example.examensarbete.GlobalVariables.UserGamesList
import com.google.firebase.firestore.FirebaseFirestore

class GetQuestions {
    lateinit var db: FirebaseFirestore

    fun getQuestions(categoryId: String) {
        db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Categories").document(categoryId).collection("questions")
                docRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val data = document.toObject(Question::class.java)
                        CurrentQuestions.currQuestions.add(data)
                        println("!!!text: ${data.text}")
                    }
                    println("!!!data: ${CurrentQuestions.currQuestions}")
                }
    }
}