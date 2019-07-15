package com.obasoglu.nbateamviewer

import com.obasoglu.nbateamviewer.di.apiModule
import com.obasoglu.nbateamviewer.di.networkModule
import com.obasoglu.nbateamviewer.di.viewModelModule
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class DIGraphTest : KoinTest {


    @Test
    fun checkDIGraph() {

        startKoin {
            modules(
                module {
                    listOf(viewModelModule, apiModule, networkModule)
                })
        }.checkModules()

        assertNotNull(viewModelModule)
        assertNotNull(apiModule)
        assertNotNull(networkModule)

    }


}
