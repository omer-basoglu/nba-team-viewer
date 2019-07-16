package com.obasoglu.nbateamviewer.screens.teampage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.obasoglu.nbateamviewer.data.Team

/**
 * ViewModel for TeamsPageFragment
 */
class TeamsPageViewModel : ViewModel() {

    // Team LiveData
    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    /**
     * Set Team to LiveData
     */
    fun setTeam(nbaTeam: Team) {
        _team.value = nbaTeam
    }

}
