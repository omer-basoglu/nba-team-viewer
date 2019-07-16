package com.obasoglu.nbateamviewer.di

import com.obasoglu.nbateamviewer.data.network.NbaApi
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Api Module of DI
 */
val apiModule = module {
    single(createdAtStart = false) {
        get<Retrofit>().create(NbaApi::class.java)
    }
}