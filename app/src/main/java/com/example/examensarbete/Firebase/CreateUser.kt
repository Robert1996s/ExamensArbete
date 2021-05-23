package com.example.examensarbete.Firebase

import android.widget.Toast
import com.example.examensarbete.DataClasses.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.security.Permission

class CreateUser {
    lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    fun userCreate(email: String, password: String, name: String) {
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        if (email == "" || password == "" || name =="") {
            println("!!!User NOT created")
        } else {
            auth.createUserWithEmailAndPassword(email, password)
            println("!!!User Created")
            saveUserData(name, email)
        }
        println("!!!name and password ${name + email}")
    }

    private fun saveUserData(name: String, email: String) {
        val user = auth.currentUser
        val userUid = user!!.uid
        val data = UserData(name, email)

        db.collection("users").add(data)
    }
}