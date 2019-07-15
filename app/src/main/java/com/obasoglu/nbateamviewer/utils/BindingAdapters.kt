package com.obasoglu.nbateamviewer.utils

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.obasoglu.nbateamviewer.data.Team
import com.obasoglu.nbateamviewer.data.network.ApiResult
import com.obasoglu.nbateamviewer.screens.teampage.PlayersListAdapter
import com.obasoglu.nbateamviewer.screens.teamslist.TeamsListAdapter


/**
 * Bind List of Teams to recyclerview in XML
 *
 */
@BindingAdapter("setTeams")
fun bindTeamsRecyclerView(recyclerView: RecyclerView, data: List<Team>?) {
    val adapter = recyclerView.adapter as TeamsListAdapter
    adapter.submitList(data)
    //Scroll to top position after filtering or setting data
    //recyclerView.smoothScrollToPosition(recyclerView.top)
}

/**
 * Bind List of Players to recyclerview in XML
 *
 */
@BindingAdapter("setTeam")
fun bindPlayersRecyclerView(recyclerView: RecyclerView, data: Team) {
    val adapter = recyclerView.adapter as PlayersListAdapter
    adapter.submitData(data)
    //Scroll to top position after filtering or setting data
    recyclerView.smoothScrollToPosition(recyclerView.top)
}


@BindingAdapter("visibilityStatus")
fun bindStatus(progressBar: ProgressBar, result: ApiResult?) {
    if (result?.teams == null) {
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }
}

@BindingAdapter("visibilityStatus")
fun bindStatus(recyclerView: RecyclerView, result: ApiResult?) {
    if (result?.teams != null) {
        recyclerView.visibility = View.VISIBLE
    } else {
        recyclerView.visibility = View.GONE
    }
}
