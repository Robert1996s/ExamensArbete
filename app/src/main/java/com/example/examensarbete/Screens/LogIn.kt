package com.example.examensarbete.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.examensarbete.Firebase.CreateUser
import com.example.examensarbete.R

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        val emailInput = findViewById<TextView>(R.id.user_name_text)
        val passwordInput = findViewById<TextView>(R.id.user_password_text)
        val logInButton = findViewById<Button>(R.id.singIn_button)
        val signUpText = findViewById<TextView>(R.id.signUp_text)

        signUpText.setOnClickListener{
            toSignUp()
        }

        logInButton.setOnClickListener {
            val userName = emailInput.text.toString()
            val userPassword = passwordInput.text.toString()
            CreateUser().userCreate(userName, userPassword)
        }
    }

    private fun toSignUp() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }
}