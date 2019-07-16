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
    private val viewModel by inject<TeamsListViewModel>()


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
        assertThat(viewModel, notNullValue())
    }

    @Test
    fun loadTeamsSuccessCorrectSetToLiveData() {
        //Arrange
        val player = Player(345, "Kawhi", "Leonard", "NA", 2)
        val team = Team(10, "Raptors", 12, 24, listOf(player))
        testSingle = Single.just(arrayListOf(team))
        Mockito.`when`(api.getTeams()).thenReturn(testSingle)
        val apiResult = ApiResult(teams = arrayListOf(team))

        //Act
        viewModel.retrieveTeams()

        //Assert
        Assert.assertEquals(viewModel.apiResult.value, apiResult)
    }

    @Test
    fun loadTeamsFailureErrorSetToLiveData() {
        //Arrange
        testSingle = Single.just(arrayListOf())
        val error = R.string.failure
        Mockito.`when`(api.getTeams()).thenReturn(testSingle)
        val apiResult = ApiResult(error = error)

        //Act
        viewModel.retrieveTeams()

        //Assert
        Assert.assertEquals(viewModel.apiResult.value, apiResult)
    }

    //TODO: Test sorting
    @Test
    fun sortTeamsAlphabetically() {
        //Arrange
        val player = Player(345, "Kawhi", "Leonard", "NA", 2)
        val team = Team(10, "Raptors", 12, 12, listOf(player))
        val team2 = Team(10, "Aaptors", 14, 2, listOf(player))
        val team3 = Team(10, "Zaptors", 6, 24, listOf(player))
        testSingle = Single.just(arrayListOf(team, team2, team3))
        Mockito.`when`(api.getTeams()).thenReturn(testSingle)
        val orderList = arrayListOf(team, team2, team3).sortedBy { it.fullName }.toMutableList()
        val orderedResult = ApiResult(teams = orderList)

        //Act
        viewModel.retrieveTeams()
        viewModel.orderTeams(R.id.order_alphabetically)

        //Assert
        Assert.assertEquals(viewModel.apiResult.value, orderedResult)

    }

    @Test
    fun sortTeamsByWins() {
        //Arrange
        val player = Player(345, "Kawhi", "Leonard", "NA", 2)
        val team = Team(10, "Raptors", 12, 12, listOf(player))
        val team2 = Team(10, "Aaptors", 14, 2, listOf(player))
        val team3 = Team(10, "Zaptors", 6, 24, listOf(player))
        testSingle = Single.just(arrayListOf(team, team2, team3))
        Mockito.`when`(api.getTeams()).thenReturn(testSingle)
        val orderList = arrayListOf(team, team2, team3).sortedBy { it.wins }.toMutableList()
        val orderedResult = ApiResult(teams = orderList)

        //Act
        viewModel.retrieveTeams()
        viewModel.orderTeams(R.id.order_wins)

        //Assert
        Assert.assertEquals(viewModel.apiResult.value, orderedResult)

    }

    @Test
    fun sortTeamsByLosses() {
        //Arrange
        val player = Player(345, "Kawhi", "Leonard", "NA", 2)
        val team = Team(10, "Raptors", 12, 12, listOf(player))
        val team2 = Team(10, "Aaptors", 14, 2, listOf(player))
        val team3 = Team(10, "Zaptors", 6, 24, listOf(player))
        testSingle = Single.just(arrayListOf(team, team2, team3))
        Mockito.`when`(api.getTeams()).thenReturn(testSingle)
        val orderList = arrayListOf(team, team2, team3).sortedBy { it.losses }.toMutableList()
        val orderedResult = ApiResult(teams = orderList)

        //Act
        viewModel.retrieveTeams()
        viewModel.orderTeams(R.id.order_losses)

        //Assert
        Assert.assertEquals(viewModel.apiResult.value, orderedResult)

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