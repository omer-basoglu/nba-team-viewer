package com.obasoglu.nbateamviewer.screens.teamslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.obasoglu.nbateamviewer.R
import com.obasoglu.nbateamviewer.data.Team
import com.obasoglu.nbateamviewer.data.network.ApiResult
import com.obasoglu.nbateamviewer.data.network.NbaApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class TeamsListViewModel(private val api: NbaApi) : ViewModel() {

    private val _apiResult = MutableLiveData<ApiResult>()
    val apiResult: LiveData<ApiResult>
        get() = _apiResult

    private val _navigateToTeamDetailFragment = MutableLiveData<Team>()
    val navigateToDetailFragment: LiveData<Team>
        get() = _navigateToTeamDetailFragment

    private val disposable = CompositeDisposable()

    fun retrieveTeams() {
        disposable.add(
            api.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Team>>() {
                    override fun onSuccess(teams: ArrayList<Team>) {
                        if (teams.isNotEmpty()) {
                            _apiResult.value = ApiResult(teams = teams)
                        } else {
                            Timber.d("Failure on Retrieving data")
                            _apiResult.value = ApiResult(error = R.string.failure)
                        }
                    }

                    override fun onError(e: Throwable) {
                        Timber.d("Error on Retrieving data: ${e.message}}")
                        _apiResult.value = ApiResult(error = R.string.failure)
                    }
                })
        )

    }

    fun sortTeams(itemID: Int) {
        Timber.d("Inside sortTeams")
        if (_apiResult.value?.teams != null) {
            when (itemID) {
                R.id.order_alphabetically ->
                    _apiResult.value =
                        ApiResult(teams = _apiResult.value?.teams?.sortedBy { it.fullName }?.toMutableList())
                R.id.order_wins -> _apiResult.value =
                    ApiResult(teams = _apiResult.value?.teams?.sortedBy { it.wins }?.toMutableList())
                R.id.order_losses -> _apiResult.value =
                    ApiResult(teams = _apiResult.value?.teams?.sortedBy { it.losses }?.toMutableList())
            }
            Timber.d("Sort result:: ${_apiResult.value?.teams?.toString()}")
        } else {
            //TODO: Inform view to toast
            //Toast.makeText(context, R.string.noData, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Set livedata to navigate to DetailsFragment
     */
    fun displayTeamDetails(team: Team) {
        _navigateToTeamDetailFragment.value = team
    }

    /**
     * Set livedata to null for unwanted navigation to DetailsFragment
     */
    fun displayTeamDetailsCompleted() {
        _navigateToTeamDetailFragment.value = null
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}