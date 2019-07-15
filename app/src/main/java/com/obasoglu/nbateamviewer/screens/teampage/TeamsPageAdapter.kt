package com.obasoglu.nbateamviewer.screens.teampage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obasoglu.nbateamviewer.R
import com.obasoglu.nbateamviewer.data.Player
import com.obasoglu.nbateamviewer.data.Team
import com.obasoglu.nbateamviewer.databinding.ItemPlayersListBinding
import com.obasoglu.nbateamviewer.databinding.ItemTeamsListBinding


class PlayersListAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private lateinit var team: Team

    override fun getItemCount(): Int {
        return team.players.count() + 1 // +1 for team details
    }

    // We submitList using BindingAdapter method setData in XML

    fun submitData(nbaTeam: Team) {
        team = nbaTeam
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_teams_list
            else -> R.layout.item_players_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        when (viewType) {
            R.layout.item_teams_list -> return TeamDetailViewHolder(
                ItemTeamsListBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    )
                )
            )
            R.layout.item_players_list -> return PlayerViewHolder(
                ItemPlayersListBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    )
                )
            )
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PlayerViewHolder -> holder.bind(team.players[position - 1])
            is TeamDetailViewHolder -> holder.bind(team)
            else -> throw IllegalArgumentException()
        }
    }

    class PlayerViewHolder(private var binding: ItemPlayersListBinding) : BaseViewHolder<Player>(binding.root) {
        override fun bind(t: Player) {
            binding.player = t
            binding.executePendingBindings()
        }
    }

    class TeamDetailViewHolder(private var binding: ItemTeamsListBinding) : BaseViewHolder<Team>(binding.root) {
        override fun bind(t: Team) {
            binding.team = t
            binding.executePendingBindings()
        }
    }

}

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(t: T)
}