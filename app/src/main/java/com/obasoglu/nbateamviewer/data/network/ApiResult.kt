package com.obasoglu.nbateamviewer.data.network

import com.obasoglu.nbateamviewer.data.Team


/**
 * Api result for team list endpoint
 * @param[teams] Mutable list of Nba Teams
 * @param[error] Error message
 */

data class ApiResult(
    val teams: MutableList<Team>? = null,
    val error: Int? = null
)
