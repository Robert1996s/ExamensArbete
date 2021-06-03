package com.example.examensarbete.Screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import com.example.examensarbete.Firebase.CreateUser
import com.example.examensarbete.R

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val user_Name = findViewById<TextView>(R.id.edit_username)
        val user_Email = findViewById<TextView>(R.id.edit_email)
        val user_Password = findViewById<TextView>(R.id.edit_password)
        var passwordConfirm = findViewById<TextView>(R.id.confirm_password)

        val signUpBtn = findViewById<Button>(R.id.button_signUp)

        signUpBtn.setOnClickListener {
            val userName = user_Name.text.toString()
            val userEmail = user_Email.text.toString()
            val userPassword = user_Password.text.toString()
            val confirmPass = passwordConfirm.text.toString()

            val thread = Thread(Runnable {
                println("!!!Creating user")
                CreateUser().userCreate(userEmail, userPassword, userName)
                checkEmailValidation(userEmail)
            })
            thread.start()
        }

    }
     fun checkEmailValidation(email: String): Boolean {
        if (email == "") {
            return false
            println("!!!email NOT good")
        } else {
            println("!!!email good")
            return true
        }

    }
}