package com.obasoglu.nbateamviewer.screens.teampage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obasoglu.nbateamviewer.R
import com.obasoglu.nbateamviewer.data.Team
import com.obasoglu.nbateamviewer.databinding.ItemPlayersListBinding
import com.obasoglu.nbateamviewer.databinding.ItemTeamsListBinding
import com.obasoglu.nbateamviewer.screens.BaseViewHolder
import com.obasoglu.nbateamviewer.screens.PlayerViewHolder
import com.obasoglu.nbateamviewer.screens.TeamViewHolder

/**
 * Adapter for TeamsPageFragment
 */
class PlayersListAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private lateinit var team: Team

    override fun getItemCount(): Int {
        return team.players.count() + 1 // +1 for team details
    }

    // We submitList using BindingAdapter method setData in XML
    fun submitData(nbaTeam: Team) {
        team = nbaTeam
    }

    // Set type based on position
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_teams_list
            else -> R.layout.item_players_list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        //Set ViewHolder based on type
        when (viewType) {
            R.layout.item_teams_list -> return TeamViewHolder(
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
            is PlayerViewHolder -> holder.bind(team.players[position - 1]) // -1 because their is team details
            is TeamViewHolder -> holder.bind(team)
            else -> throw IllegalArgumentException()
        }
    }
}

