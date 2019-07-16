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

/**
 * ViewModel for TeamsListFragment
 */
class TeamsListViewModel(private val api: NbaApi) : ViewModel() {

    // Api Result
    private val _apiResult = MutableLiveData<ApiResult>()
    val apiResult: LiveData<ApiResult>
        get() = _apiResult

    // LiveData dor toast message
    private val _toastMessage = MutableLiveData<Int>()
    val toastMessage: LiveData<Int>
        get() = _toastMessage

    // LiveData for loading status
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    // LiveData for Navigation to TeamsDetailsFragment
    private val _navigateToTeamDetailFragment = MutableLiveData<Team>()
    val navigateToDetailFragment: LiveData<Team>
        get() = _navigateToTeamDetailFragment

    // RxJava disposable
    private val disposable = CompositeDisposable()

    /**
     * Retrieve teams using RxJava Single
     * Works on io thread, retrieves data on MainThread
     */
    fun retrieveTeams() {
        displayLoadingProgress(true)
        disposable.add(
            api.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Team>>() {
                    override fun onSuccess(teams: ArrayList<Team>) {
                        displayLoadingProgress(false)
                        if (teams.isNotEmpty()) {
                            _apiResult.value = ApiResult(teams = teams)
                        } else {
                            Timber.d("Failure on Retrieving data")
                            _apiResult.value = ApiResult(error = R.string.failure)
                        }
                    }

                    override fun onError(e: Throwable) {
                        Timber.d("Error on Retrieving data: ${e.message}}")
                        displayLoadingProgress(false)
                        _apiResult.value = ApiResult(error = R.string.failure)
                    }
                })
        )
    }

    /**
     *Order teams - by alphabetically, wins or losses
     */
    fun orderTeams(itemID: Int) {
        Timber.d("Sorting teams")
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
            displayToast(R.string.noDataFilter)
        }
    }

    /**
     * Set livedata to navigate to DetailsFragment
     */
    fun navigateTeamDetails(team: Team) {
        _navigateToTeamDetailFragment.value = team
    }

    /**
     * Set livedata to null for unwanted navigation to DetailsFragment
     */
    fun navigateTeamDetailsCompleted() {
        _navigateToTeamDetailFragment.value = null
    }

    /**
     * Set message to display Toast message
     */
    fun displayToast(message: Int) {
        _toastMessage.value = message
    }

    /**
     * Set livedata to null for unwanted Toast messages
     */
    fun displayToastCompleted() {
        _toastMessage.value = null
    }

    /**
     * Set livedata to loading=show progress bar
     * [status] set true to show, set false to hide
     */
    fun displayLoadingProgress(status: Boolean) {
        _loading.value = status
    }

    override fun onCleared() {
        super.onCleared()
        //Clear disposable
        disposable.clear()
    }


}