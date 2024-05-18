package com.example.movielistapp.mvvm.views.fragment.latestMovieFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.mvvm.base.BaseViewModel
import com.example.movielistapp.MoviesAdapter
import com.example.movielistapp.MyApplication
import com.example.movielistapp.Utils.NetworkStateData
import com.example.movielistapp.network.NetworkStatusHelper
import com.example.networksdk.Movie
import com.example.networksdk.MovieDetails
import com.test.networksdk.NetworkSDK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LatestMoviesVM @Inject constructor(
    val myApplication: MyApplication,
    networkStateData: NetworkStatusHelper,
)
: BaseViewModel(networkStateData) {


     var popularMovieArrayList:List<Movie>?=null
    val networkSDK = NetworkSDK()
    var latestMovieResposne = MutableLiveData<MovieDetails>()


    fun getLatestMovie(){
        viewModelScope.launch(Dispatchers.IO) {
            val latestMoviesData = networkSDK.getLatestMovie()
            Log.d("latestMoviesDetails", "MovieDetails Res: ${latestMoviesData}")
            latestMovieResposne.postValue(latestMoviesData)


        }
    }
}