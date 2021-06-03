package com.example.examensarbete.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.examensarbete.DataClasses.CurrentGame

@Database(entities = [CurrentGame::class], version = 1)
abstract class myRoomDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}