package com.example.examensarbete.Screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examensarbete.Adapters.GameAdapter
import com.example.examensarbete.DataClasses.CurrentGame
import com.example.examensarbete.R
import com.example.examensarbete.ViewModels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class PlayerProfile : AppCompatActivity() {

    private var userGames = mutableListOf<CurrentGame>()
    lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var uid = 0
    private var userUid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_profile)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val userName = findViewById<TextView>(R.id.player_name_text)
        val signOutBtn = findViewById<Button>(R.id.logOut_button)
        val switch = findViewById<Switch>(R.id.switchList)

        switch.setOnCheckedChangeListener({ _ , isChecked ->
            val message = if (isChecked) "Sorted by Score" else "Sorted by Recent"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        })

        signOutBtn.setOnClickListener {
            //auth.signOut()
        }

        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            userUid = currentUser.uid
        }

        val userProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        userProfileViewModel.getUserInfo(userUid)

        userProfileViewModel.setUserName().observe(this, Observer {
            userName.text = it.toString()
            println("!!! new value: ${it}")
        })

        val gameList = db.collection("users").document(uid.toString()).collection("finishedGames")


        val adapter = GameAdapter(this, userGames)
        val recyclerView = findViewById<RecyclerView>(R.id.all_player_games)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val playerName = findViewById<TextView>(R.id.player_name_text)


        gameList.addSnapshotListener { snapshot, error ->
            if (snapshot != null) {
                userGames.clear()
                for (document in snapshot.documents) {
                    val newGame = document.toObject(CurrentGame::class.java)
                    if (newGame != null) {
                        userGames.add(newGame)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

        
        userGames.add(0, CurrentGame("player_uid", 5, "History", true))
        userGames.add(1, CurrentGame("player_uid", 4, "Sport", true))
        userGames.add(2, CurrentGame("player_uid", 8, "Music", true))
        userGames.add(3, CurrentGame("player_uid", 2, "History", true))



        fun sortList () {
            var i = 0
            userGames[i].score
        }
        fun bubbleSort (games: ArrayList<CurrentGame>):ArrayList<CurrentGame> {
            println("!!!Sort started")
            var swap = true
            var letters = arrayListOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","å","ä","ö")

            while (swap) {
                swap = false
                for (i in 0 until games.size -1){
                    val wordCheck = games[i]
                    val nextWordCheck = games[i + 1]
                    //val first = wordCheck.substring(0,1)
                    //val second = wordsToCheckNext.substring(0,1)
                }
            }
            return games
        }


    }

}