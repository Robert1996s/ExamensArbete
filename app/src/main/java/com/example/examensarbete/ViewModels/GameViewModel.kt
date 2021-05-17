package com.example.examensarbete.ViewModels

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    var player_score = 0

    fun addScore(){
        player_score++
    }

    override fun onCleared() {
        super.onCleared()
    }
}