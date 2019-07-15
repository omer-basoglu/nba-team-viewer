package com.obasoglu.nbateamviewer.screens

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.obasoglu.nbateamviewer.data.Player
import com.obasoglu.nbateamviewer.data.Team
import com.obasoglu.nbateamviewer.di.apiModule
import com.obasoglu.nbateamviewer.di.networkModule
import com.obasoglu.nbateamviewer.di.viewModelModule
import com.obasoglu.nbateamviewer.screens.teampage.TeamsPageViewModel
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject


/**
 * Unit Test of [TeamDetailViewModel]
 */
class TeamDetailViewModelTest : AutoCloseKoinTest() {

    // region constants

    // endregion constants

    // region helper fields
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    // endregion helper fields

    //SUT
    private val teamDetailViewModel by inject<TeamsPageViewModel>()


    @Before
    fun setup() {
        // Initialize Koin
        startKoin {
            // module list
            modules(listOf(viewModelModule, apiModule, networkModule))
        }
    }

    @Test
    fun viewModelNotNull() {
        assertThat(teamDetailViewModel, notNullValue())
    }

    @Test
    fun setValueMethodSetsCorrectDataOnLiveData() {
        //Arrange
        val player = Player(345, "Kawhi", "Leonard", "NA", 2)
        val team = Team(10, "Raptors", 12, 24, listOf(player))

        //Act
        teamDetailViewModel.setTeam(team)

        //Assert
        Assert.assertEquals(teamDetailViewModel.team.value, team)
    }


    // region helpers

    // endregion helpers

}