package com.obasoglu.nbateamviewer.screens.teamslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.obasoglu.nbateamviewer.databinding.ItemTeamsListBinding
import com.obasoglu.nbateamviewer.data.Team

class TeamsListAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Team, TeamsListAdapter.TeamViewHolder>(DiffCallBack) {

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

    class TeamViewHolder(private var binding: ItemTeamsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team) {
            binding.team = team
            binding.executePendingBindings()
        }
    }


    companion object DiffCallBack : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == oldItem.id
        }

    }

    class OnClickListener(val clickListener: (team: Team) -> Unit) {
        fun onClick(team: Team) = clickListener(team)
    }
}