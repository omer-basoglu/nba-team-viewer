package com.obasoglu.nbateamviewer.utils

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.obasoglu.nbateamviewer.data.Team
import com.obasoglu.nbateamviewer.data.network.ApiResult
import com.obasoglu.nbateamviewer.screens.teampage.PlayersListAdapter
import com.obasoglu.nbateamviewer.screens.teamslist.TeamsListAdapter

/**
 * Binding adapter for binding data in XML
 */

/**
 * Bind List of Teams (setData) to recyclerview in XML
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
 * Bind List of Players (setData) to recyclerview in XML
 *
 */
@BindingAdapter("setTeam")
fun bindPlayersRecyclerView(recyclerView: RecyclerView, data: Team) {
    val adapter = recyclerView.adapter as PlayersListAdapter
    adapter.submitData(data)
    //Scroll to top position after filtering or setting data
    recyclerView.smoothScrollToPosition(recyclerView.top)
}

/**
 * Set visiblity status based on [Boolean]
 * @param status if false hides the [view] else shows
 */
@BindingAdapter("visibilityStatus")
fun bindStatus(view: View, status: Boolean?) {
    status?.let {
        if (status) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}

/**
 * Set visiblity status based on [ApiResult]
 * @param result if empty hides the [RecyclerView] else shows
 * @param result if not empty hides the [TextView] else shows
 */
@BindingAdapter("visibilityStatus")
fun bindStatus(view: View, result: ApiResult?) {
    if (view is RecyclerView) {
        if (result?.teams != null) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    } else if (view is TextView) {
        if (result?.error != null) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}
