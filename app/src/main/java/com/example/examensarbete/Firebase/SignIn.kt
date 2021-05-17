package com.example.examensarbete.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignIn {

    private lateinit var auth: FirebaseAuth

    fun signInUser (email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //TODO navigate to MainActivity
                    println("!!!Logged in")
                }
            }
    }
}