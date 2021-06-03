package com.example.examensarbete.Screens

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.examensarbete.NetWork.NetworkHandler
import com.example.examensarbete.R

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playBtn = findViewById<Button>(R.id.play_button)
        val profileBtn = findViewById<ImageView>(R.id.profile_button)
        val howToPlayText = findViewById<TextView>(R.id.howTo_text)


        //Shows the infopage about the game
        val buttonClicked = {dialog: DialogInterface, which: Int ->
            println("!!! Nice")
        }

        //Alert that shows the how to play text
        val alertText = AlertDialog.Builder(this)
        alertText.setTitle("How to Play?")
        alertText.setMessage("This is how you play the 50/50 game. " +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")
        alertText.setPositiveButton("Got it!", DialogInterface.OnClickListener(function = buttonClicked))

        //val testObj = CurrentGame("testObj", 10, "Test", true)
        //val testObj2 = CurrentGame("testObj", 2, "Test", true)


        playBtn.setOnClickListener {
            goToCategories()
        }
        profileBtn.setOnClickListener {
            goToProfile()
        }
        howToPlayText.setOnClickListener {
            alertText.show()
        }
    }

    //Navigation to Profile
    private fun goToProfile() {
        val intent = Intent(this, PlayerProfile::class.java)
        startActivity(intent)
    }

    //Navigation to Categories
    private fun goToCategories() {
        val intent = Intent(this, Categories::class.java)
        startActivity(intent)
    }


    //If user press back button, show text if user wants to exit the app
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Exit app?")
            .setMessage("Are you sure you want to exit?")
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(android.R.string.yes, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    finish()
                }
            }).create().show()
    }

}