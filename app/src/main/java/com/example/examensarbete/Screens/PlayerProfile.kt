package com.example.examensarbete.Screens

import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examensarbete.Adapters.GameAdapter
import com.example.examensarbete.Cache.LruCache
import com.example.examensarbete.DataClasses.CurrentGame
import com.example.examensarbete.Encryption.DataEncryption
import com.example.examensarbete.GlobalVariables.UserGamesList
import com.example.examensarbete.NetWork.NetworkHandler
import com.example.examensarbete.R
import com.example.examensarbete.ViewModels.ProfileViewModel
import com.example.examensarbete.ViewModels.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class PlayerProfile : AppCompatActivity() {

    private var userGames = mutableListOf<CurrentGame>()
    private lateinit var auth: FirebaseAuth
    private var userUid = ""
    private lateinit var viewModel: ProfileViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_profile)

        auth = FirebaseAuth.getInstance()

        val adapter = GameAdapter(this, UserGamesList.globalUserGames) // globala listan??
        val recyclerView = findViewById<RecyclerView>(R.id.all_player_games)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val userName = findViewById<TextView>(R.id.player_name_text)
        val signOutBtn = findViewById<Button>(R.id.logOut_button)
        val switch = findViewById<Switch>(R.id.switchList)
        val recentText = findViewById<TextView>(R.id.recent_game_text)
        val editProfileBtn = findViewById<ImageButton>(R.id.edit_profile)

        //val userProfileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)


        //val myWordViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(ProfileViewModel::class.java)


        //viewModel = ViewModelProvider.of(this, ViewModelFactory(this@PlayerProfile))
                //.get(ProfileViewModel::class.java)


        //kan inte importera .of
        //Sista biten i en fungerande cache, sedan ska det fungera...
        //https://stackoverflow.com/questions/64834877/androidviewmodel-has-no-zero-argument-constructor-how-to-solve-this
        //https://stackoverflow.com/questions/44194579/android-viewmodel-has-no-zero-argument-constructor


        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)





        val cache = LruCache<String, String>(8)

        editProfileBtn.setOnClickListener {
            //TODO EDIT PROFILE FUNCTION
        }


        switch.setOnCheckedChangeListener({ _ , isChecked ->
            val message = if (isChecked) "Sorted alphabetical" else "Sorted by Score"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            if (isChecked) {
                UserGamesList.globalUserGames = bubbleSort(UserGamesList.globalUserGames)
                adapter.notifyDataSetChanged()
            } else {
                UserGamesList.globalUserGames = sortScore(UserGamesList.globalUserGames)
                adapter.notifyDataSetChanged()
            }
        })

        signOutBtn.setOnClickListener {
            //auth.signOut()
        }

        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            userUid = currentUser.uid
        }

        println("!!! UID: $userUid")

        if (NetworkHandler.isOnline(this)) {
            //userProfileViewModel.getGames(userUid)
            viewModel.getCache()

            adapter.notifyDataSetChanged()
        } else {
            viewModel.setUpCache()
            adapter.notifyDataSetChanged()
        }

        //userProfileViewModel.getUserInfo(userUid)

        viewModel.getUserInfo(userUid)

        /*userProfileViewModel.setUserName().observe(this, Observer {
            userName.text = it.toString()
            println("!!! new value username: ${it}")
        })

        
        UserGamesList.globalUserGames.clear()

        userProfileViewModel.getGameList().observe(this, Observer {
            userGames = it
            println("!!! userGames : $userGames" +
                    "")
            //bubbleSort(UserGamesList.globalUserGames)
            adapter.notifyDataSetChanged()
        }) */

        val message = "secret message"

        //Encryption, calling the encryption class and encrypts a string
        var map = DataEncryption()
                .encrypt(message.toByteArray(), "Secret message".toCharArray())
        val base64Encrypted = Base64.encodeToString(map["encrypted"], Base64.NO_WRAP)
        val base64Iv = Base64.encodeToString(map["iv"], Base64.NO_WRAP)
        val base64Salt = Base64.encodeToString(map["salt"], Base64.NO_WRAP)

        println("!!! Encrypted Message:"+base64Encrypted)
        //Decoding
        val encrypted = Base64.decode(base64Encrypted, Base64.NO_WRAP)
        val iv = Base64.decode(base64Iv, Base64.NO_WRAP)
        val salt = Base64.decode(base64Salt, Base64.NO_WRAP)
        val decrypted = DataEncryption()
                .decrypt(hashMapOf("iv" to iv, "salt" to salt, "encrypted" to encrypted), "Secret message".toCharArray())

        //Decrypting the message to string
        var decryptedMessage: String? = null
        decrypted?.let {
            decryptedMessage = String(it, Charsets.UTF_8)
        }

        println("!!!Decrypted message: $decryptedMessage")


        /*userGames.addSnapshotListener { snapshot, error ->
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
        } */
    }

    private fun sortScore (games: MutableList<CurrentGame>) :MutableList<CurrentGame> {
        var swap = true
        var numbers = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8)

        while (swap) {
            swap = false
            for (i in 0 until games.size -1) {
                val scoreCheck = games[i].score
                val nextScoreCheck = games[i +1].score

                if (scoreCheck < nextScoreCheck) {
                    val temp = games[i].score
                    games[i].score = games[i + 1].score
                    games[i +1].score = temp
                    swap = true
                }
            }
        }
        return games
    }

    //Sorting all games by alphabetical order (category)
    private fun bubbleSort (games: MutableList<CurrentGame>):MutableList<CurrentGame> {
        println("!!!Sort started")
        var swap = true
        var letters = arrayListOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","??","??","??")

        while (swap) {
            swap = false
            for (i in 0 until games.size -1){
                val wordCheck = games[i].category.toString()
                val nextWordCheck = games[i + 1].category.toString()
                val first = wordCheck.substring(0,1)
                val second = nextWordCheck.substring(0,1)
                val firstWord = letters.indexOf(first.toLowerCase())
                val secondWord = letters.indexOf(second.toLowerCase())

                if (firstWord > secondWord) {
                    val temp = games[i].category.toString()
                    games[i].category = games[i + 1].category.toString()
                    games[i + 1].category = temp
                    swap = true
                }
            }
        }
        return games
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCache() {
        val cache = LruCache<String, String>(8)
        val hej = cache.get("0")
        println("!!!cache data : $hej")
    }
}