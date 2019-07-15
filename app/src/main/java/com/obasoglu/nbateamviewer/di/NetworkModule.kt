package com.obasoglu.nbateamviewer.di

import com.obasoglu.nbateamviewer.utils.*
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



/**
 * Network module
 */
val networkModule = module {

    single {
        Cache(androidContext().cacheDir, 5 * 1024 * 1024) // 5 MB cache
    }

    single {
        OkHttpClient.Builder().apply {
            cache(get())
            // Add an Interceptor to the OkHttpClient
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(get())
        }.build()
    }

    single {
        Interceptor { chain ->
            // Get the request from the chain.
            var request = chain.request()

            request = if (hasNetwork(androidContext())!!) {
                /*
            *  If there is Internet, get the cache that was stored 5 seconds ago.
            *  If the cache is older than 5 seconds, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-age' attribute is responsible for this behavior.
            */
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            } else {
                /*
               *  If there is no Internet, get the cache that was stored 3 days ago.
               *  If the cache is older than 3 days, then discard it,
               *  and indicate an error in fetching the response.
               *  The 'max-stale' attribute is responsible for this behavior.
               *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
               */
                request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 3
                ).build()
            }

            // Add the modified request to the chain.
            chain.proceed(request)
        }
    }

    single {
        Retrofit.Builder().baseUrl(BASE_URL).client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}

