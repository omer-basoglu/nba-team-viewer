package com.obasoglu.nbateamviewer.screens.teamslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.obasoglu.nbateamviewer.data.Team
import com.obasoglu.nbateamviewer.databinding.ItemTeamsListBinding
import com.obasoglu.nbateamviewer.screens.TeamViewHolder

/**
 * Adapter for Teams List RecyclerView. ListAdapter also provides some nice animations by default
 */
class TeamsListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Team, TeamViewHolder>(DiffCallBack) {

    // We submitList using BindingAdapter method setData in XML

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(ItemTeamsListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = getItem(position)
        holder.bind(team)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(team)
        }
    }

    /**
     * To detect only items that changed
     */
    companion object DiffCallBack : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == oldItem.id
        }

    }

    /**
     * ClickListener to detects clicks on items
     */
    class OnClickListener(val clickListener: (team: Team) -> Unit) {
        fun onClick(team: Team) = clickListener(team)
    }
}