package com.obasoglu.nbateamviewer.screens

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.obasoglu.nbateamviewer.data.Player
import com.obasoglu.nbateamviewer.data.Team
import com.obasoglu.nbateamviewer.databinding.ItemPlayersListBinding
import com.obasoglu.nbateamviewer.databinding.ItemTeamsListBinding

/**
 * BaseViewHolder
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(t: T)
}

/**
 * Player ViewHolder
 */
class PlayerViewHolder(private var binding: ItemPlayersListBinding) : BaseViewHolder<Player>(binding.root) {
    override fun bind(t: Player) {
        binding.player = t
        binding.executePendingBindings()
    }
}

/**
 * Team ViewHolder
 */
class TeamViewHolder(private var binding: ItemTeamsListBinding) : BaseViewHolder<Team>(binding.root) {
    override fun bind(t: Team) {
        binding.team = t
        binding.executePendingBindings()
    }
}