package com.example.examensarbete.Database

import android.content.Context
import androidx.room.Room

object GamesRoom {
    private lateinit var Instance: myRoomDatabase

    fun getInstance(context: Context): myRoomDatabase {
        if (!GamesRoom::Instance.isInitialized) {
            synchronized(myRoomDatabase::class) {
                if (!GamesRoom::Instance.isInitialized) {
                    Instance = Room.databaseBuilder(context.applicationContext,
                    myRoomDatabase::class.java,
                    "GamesRoom").fallbackToDestructiveMigration().build()
                }
            }
        }
        return Instance
    }
}