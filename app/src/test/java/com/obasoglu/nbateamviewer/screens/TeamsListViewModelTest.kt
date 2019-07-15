package com.obasoglu.nbateamviewer.screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.obasoglu.nbateamviewer.R
import com.obasoglu.nbateamviewer.data.Player
import com.obasoglu.nbateamviewer.data.Team
import com.obasoglu.nbateamviewer.data.network.ApiResult
import com.obasoglu.nbateamviewer.data.network.NbaApi
import com.obasoglu.nbateamviewer.di.apiModule
import com.obasoglu.nbateamviewer.di.viewModelModule
import com.obasoglu.nbateamviewer.network.testNetworkModule
import com.obasoglu.nbateamviewer.screens.teampage.TeamsPageViewModel
import com.obasoglu.nbateamviewer.screens.teamslist.TeamsListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * Unit Test of [TeamsListViewModel]
 */
class TeamsListViewModelTest : AutoCloseKoinTest() {

    // region constants

    // endregion constants

    // region helper fields
    private val api by inject<NbaApi>()
    private var testSingle: Single<ArrayList<Team>>? = null

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // endregion helper fields

    //SUT
    private val teamsListViewModel by inject<TeamsListViewModel>()


    @Before
    fun setup() {
        // Initialize Koin
        startKoin {
            // module list
            modules(listOf(viewModelModule, apiModule, testNetworkModule))
        }

        // Create mock for NbaApi
        declareMock<NbaApi>()

        setUpRxSchedulers()
    }

    @Test
    fun viewModelNotNull() {
        assertThat(teamsListViewModel, notNullValue())
    }

    @Test
    fun loadTeamsSuccessCorrectSetLiveData() {
        //Arrange
        val player = Player(345, "Kawhi", "Leonard", "NA", 2)
        val team = Team(10, "Raptors", 12, 24, listOf(player))
        testSingle = Single.just(arrayListOf(team))
        Mockito.`when`(api.getTeams()).thenReturn(testSingle)
        val apiResult = ApiResult(teams = arrayListOf(team))

        //Act
        teamsListViewModel.retrieveTeams()

        //Assert
        Assert.assertEquals(teamsListViewModel.apiResult.value, apiResult)
    }

    @Test
    fun loadTeamsFailureErrorSetLiveData() {
        //Arrange
        testSingle = Single.just(arrayListOf())
        val error = R.string.failure
        Mockito.`when`(api.getTeams()).thenReturn(testSingle)
        val apiResult = ApiResult(error = error)

        //Act
        teamsListViewModel.retrieveTeams()

        //Assert
        Assert.assertEquals(teamsListViewModel.apiResult.value, apiResult)
    }


    // region helpers
    @Before
    fun setUpRxSchedulers() {

        val immediate = object : Scheduler() {

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }

        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }
    // endregion helpers


}