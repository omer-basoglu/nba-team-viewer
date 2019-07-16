package com.obasoglu.nbateamviewer.di

import com.obasoglu.nbateamviewer.screens.teampage.TeamsPageViewModel
import com.obasoglu.nbateamviewer.screens.teamslist.TeamsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModel module of DI
 */
val viewModelModule = module {
    viewModel {
        TeamsListViewModel(get())
    }
    viewModel {
        TeamsPageViewModel()
    }
}