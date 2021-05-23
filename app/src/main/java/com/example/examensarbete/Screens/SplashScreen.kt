package com.example.examensarbete.Screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examensarbete.R
import java.util.*
import kotlin.concurrent.schedule

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        animateScreen()
    }

    private fun animateScreen() {
        Timer("Loading...", false).schedule(2000) {
            toNextScreen()
        }
    }

    private fun toNextScreen() {
        val intent = Intent(this, LogIn::class.java)
        startActivity(intent)
    }
}