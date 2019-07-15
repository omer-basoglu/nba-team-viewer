package com.obasoglu.nbateamviewer.di

import com.obasoglu.nbateamviewer.screens.teampage.TeamsPageViewModel
import com.obasoglu.nbateamviewer.screens.teamslist.TeamsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * ViewModel module
 */
val viewModelModule = module {
    viewModel {
        TeamsListViewModel(get())
    }
    viewModel {
        TeamsPageViewModel()
    }
}