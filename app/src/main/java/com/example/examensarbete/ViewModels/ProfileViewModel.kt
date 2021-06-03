package com.example.examensarbete.ViewModels

import android.content.Context
import android.util.LruCache
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.examensarbete.DataClasses.CurrentGame
import com.example.examensarbete.DataClasses.UserData
import com.example.examensarbete.Database.GamesRoom
import com.example.examensarbete.Database.myRoomDatabase
import com.example.examensarbete.GlobalVariables.UserGamesList
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class ProfileViewModel(val context: Context): ViewModel () {

    lateinit var db: FirebaseFirestore
    private var cache = com.example.examensarbete.Cache.LruCache<String, String>(5)


    private var roomDb: myRoomDatabase = GamesRoom.getInstance(context)
    var localGameList = ArrayList<CurrentGame>()


    private var cacheGames = mutableListOf<CurrentGame>()


    /*val roomDb = Room.databaseBuilder(
        applicationContext, myRoomDatabase::class.java, "databaseName"
    ).build()
    val gameDao = roomDb.gameDao()
    val games: List<CurrentGame> = gameDao.getAll() */


    fun setUpCache() {
        val job : CompletableJob = Job()
        CoroutineScope(Dispatchers.IO + job).launch {
            roomDb.clearAllTables()
            for (i in 0 until localGameList.size) {
                roomDb.gameDao().insertItem(localGameList[i])
                println("!!!Cache set UP : ${roomDb.gameDao()}")
            }
        }
        job.cancel()
        println("!!!setUp job : $job")
    }

    fun getCache() {
        val job : CompletableJob = Job()
        CoroutineScope(Dispatchers.IO + job).launch {
            cacheGames = roomDb.gameDao().getAll() as MutableList<CurrentGame>
            for (game in cacheGames){
                println("!!! game from cache: ${game.category}")
            }
        }
        job.cancel()
        println("!!!get cache job : $job")
    }

    private val _userName = MutableLiveData<String>()


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

        val mapper = jacksonObjectMapper()
        var jsonString = ""

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
                        //Roomcaching
                        //gameDao.insertAll(game)
                        //Caching
                        jsonString = mapper.writeValueAsString(game)
                        cache.put(cacheKey.toString(), jsonString)
                        cacheKey++
                        UserGamesList.globalUserGames.add(game)
                        println("!!!List: ${UserGamesList.globalUserGames[0]}")
                    }
                }
                cache.dump()
            }
        }
    }
}