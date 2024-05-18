package com.example.movielistapp.di

import android.content.Context
import com.example.movielistapp.Utils.NetworkStateData
import com.example.movielistapp.MyApplication
import com.example.movielistapp.network.NetworkStatusHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MyApplication {
        return app as MyApplication
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideNetworkStatusHelper(application: MyApplication) =
        NetworkStatusHelper(application)

    @Provides
    @Singleton
    fun providesNetworkState(@ApplicationContext app: Context): NetworkStateData {
        return NetworkStateData(app)
    }






}