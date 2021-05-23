package com.example.examensarbete.Firebase

import com.example.examensarbete.DataClasses.CurrentGame
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddCompletedGame {

    lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    fun addFinishedGame(score: Int, category: String) {

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        val userUid = user!!.uid

        val game = CurrentGame(userUid, score, category, true)


        db.collection("users").document(userUid).collection("gamesPlayed").add(game)
        //Add the game to db
    }
}