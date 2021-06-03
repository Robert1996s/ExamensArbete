package com.example.examensarbete.DataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CurrentGame (
        @PrimaryKey(autoGenerate = true)
        val player_id: String? = null,
        var score: Int = 0,
        var category: String? = null,
        val player_played: Boolean = false
) {

}