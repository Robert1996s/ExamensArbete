package com.example.examensarbete.Firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class CreateUser {
    //lateinit var db: FirebaseFirestore




    fun userCreate(name: String, password: String) {
        println("!!!name and password ${name + password}")
    }
}