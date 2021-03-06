package com.example.examensarbete.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.examensarbete.Firebase.CreateUser
import com.example.examensarbete.Firebase.GetUserGames
import com.example.examensarbete.Firebase.SignIn
import com.example.examensarbete.GlobalVariables.UserGamesList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.examensarbete.R
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class LogIn : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    private var userUid = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val currentUser: FirebaseUser? = auth.currentUser

        if (currentUser != null) {
            toMainActivity()
            println("!!! User: ${currentUser}")
        } else {
            println("!!! No user")
        }


        val emailInput = findViewById<TextView>(R.id.user_name_text)
        val passwordInput = findViewById<TextView>(R.id.user_password_text)
        val logInButton = findViewById<Button>(R.id.singIn_button)
        val signUpText = findViewById<TextView>(R.id.signUp_text)
        val forgotPassword = findViewById<TextView>(R.id.forgot_password)

        signUpText.setOnClickListener{
            toSignUp()
        }

        logInButton.setOnClickListener {
            val userEmail = emailInput.text.toString()
            val userPassword = passwordInput.text.toString()
            SignIn().signInUser(userEmail, userPassword)
        }

        forgotPassword.setOnClickListener {
            toForgotPassword()
        }
    }

    private fun toForgotPassword() {
        val intent = Intent(this, ForgotPassword::class.java)
        startActivity(intent)
    }

    private fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun toLogIn() {
        val intent = Intent(this, LogIn::class.java)
        startActivity(intent)
    }
    private fun toSignUp() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }

}