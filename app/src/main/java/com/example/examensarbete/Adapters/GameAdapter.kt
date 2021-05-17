package com.example.examensarbete.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examensarbete.DataClasses.CurrentGame
import com.example.examensarbete.R

class GameAdapter (
    private val context: Context,
    private val games: List<CurrentGame>): RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val gameView = layoutInflater.inflate(R.layout.row_game, parent, false)
        return ViewHolder(gameView)
    }

    override fun getItemCount() = games.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]

        val score = game.score
        val category = game.category

        holder.categoryText.text = category
        holder.playerScore.text = score.toString()

    }

    inner class ViewHolder(gameView: View) : RecyclerView.ViewHolder(gameView) {
        val categoryText: TextView = gameView.findViewById(R.id.category_text)
        val playerScore: TextView = gameView.findViewById(R.id.player_points)
    }

}

