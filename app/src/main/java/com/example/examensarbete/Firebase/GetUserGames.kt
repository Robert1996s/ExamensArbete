package com.example.examensarbete.Firebase

import com.example.examensarbete.DataClasses.CurrentGame
import com.example.examensarbete.GlobalVariables.UserGamesList
import com.google.firebase.firestore.FirebaseFirestore

class GetUserGames {

    lateinit var db: FirebaseFirestore


    fun getGames(uid: String) {
        db = FirebaseFirestore.getInstance()
        val docRef = db.collection("users").document(uid).collection("gamesPlayed")
                docRef.addSnapshotListener { snapshot, e ->
                    if (snapshot != null) {
                        UserGamesList.globalUserGames.clear()
                        for (document in snapshot.documents){
                            val game = document.toObject(CurrentGame::class.java)
                            if (game != null) {
                                UserGamesList.globalUserGames.add(game)
                                println("!!!List: ${UserGamesList.globalUserGames[0]}")
                            }
                        }
                    }
                }
    }
}