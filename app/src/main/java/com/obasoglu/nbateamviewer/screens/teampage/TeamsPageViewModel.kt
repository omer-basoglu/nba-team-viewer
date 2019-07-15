package com.obasoglu.nbateamviewer.screens.teampage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.obasoglu.nbateamviewer.data.Team

class TeamsPageViewModel : ViewModel() {

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    fun setTeam(nbaTeam: Team) {
        _team.value = nbaTeam
    }

}
