package com.obasoglu.nbateamviewer.data.network

import com.obasoglu.nbateamviewer.data.Team
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Api service
 * @param[getTeams] Get all teams
 * @return
 */
interface NbaApi {
    @GET("nba-team-viewer/master/input.json")
    fun getTeams(): Single<ArrayList<Team>>
}



