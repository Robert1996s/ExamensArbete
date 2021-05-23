package com.example.examensarbete.ViewModels

import android.util.LruCache
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examensarbete.DataClasses.CurrentGame
import com.example.examensarbete.DataClasses.UserData
import com.example.examensarbete.GlobalVariables.UserGamesList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ProfileViewModel: ViewModel () {

    lateinit var db: FirebaseFirestore
    private var cache = com.example.examensarbete.Cache.LruCache<String, String>(5)


    private val _userName = MutableLiveData<String>()

    var localGameList = ArrayList<CurrentGame>()

    fun setUserName(): LiveData<String> {
        return _userName
    }

    private val _gameList = MutableLiveData<MutableList<CurrentGame>>()

    fun getGameList(): MutableLiveData<MutableList<CurrentGame>>{
        return _gameList
    }

    fun setGameList(game: CurrentGame){
        localGameList.add(game)
        _gameList.value = localGameList
    }

    fun getUserInfo(uid: String) {
        db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)
        userRef.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document!!.exists()) {
                            println("!!! Firebase User: ${document.data}")
                            val userData = document.toObject(UserData::class.java)!!
                            _userName.value = userData.user_name

                        }
                    }
                    else {
                        println("!!! Get profile went wrong")
                    }
        }

    }

    fun getGames(uid: String) {



        db = FirebaseFirestore.getInstance()
        var cacheKey = 0
        val docRef = db.collection("users").document(uid).collection("gamesPlayed")
        docRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                //UserGamesList.globalUserGames.clear()
                for (document in snapshot.documents){
                    val game = document.toObject(CurrentGame::class.java)
                    if (game != null) {
                        setGameList(game)
                        cache.put(cacheKey.toString(), game)
                        cache.dump()
                        cacheKey++
                        println("!!!cacheKey : $cacheKey")
                        UserGamesList.globalUserGames.add(game)
                        println("!!!List: ${UserGamesList.globalUserGames[0]}")
                    }
                }
            }
        }
    }
}