package com.example.examensarbete.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.examensarbete.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var userEmail = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        val emailInput = findViewById<TextView>(R.id.email_input)
        val sendBtn = findViewById<Button>(R.id.send_email_button)

        emailInput.text = userEmail

        sendBtn.setOnClickListener {
            sendPassword(userEmail)
        }


    }

    private fun sendPassword(email: String) {
        if (email != "") {
            auth.sendPasswordResetEmail(email)
            //navigateBack()
        } else {
            println("!!! No email filled in")
        }
    }

    private fun navigateBack() {
        val intent = Intent(this, LogIn::class.java)
        startActivity(intent)
    }
}