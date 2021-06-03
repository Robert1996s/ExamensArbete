package com.example.examensarbete.Database

import androidx.room.*
import com.example.examensarbete.DataClasses.CurrentGame

@Dao

interface GameDao {
    @Query("SELECT * FROM CURRENTGAME")
    fun getAll(): List<CurrentGame>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(game: CurrentGame)

    @Delete
    fun delete(game: CurrentGame)
}