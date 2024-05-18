package com.example.movielistapp.mvvm.views.fragment.popularMovieFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.mvvm.base.BaseViewModel
import com.example.movielistapp.MoviesAdapter
import com.example.movielistapp.MyApplication
import com.example.movielistapp.Utils.NetworkStateData
import com.example.movielistapp.network.NetworkStatusHelper
import com.example.networksdk.Movie
import com.test.networksdk.NetworkSDK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesVM @Inject constructor(
    val myApplication: MyApplication,
    networkStateData: NetworkStatusHelper,
)
: BaseViewModel(networkStateData) {


     var popularMovieArrayList:List<Movie>?=null
    var moviesAdapter: MoviesAdapter? = null
    val networkSDK = NetworkSDK()
    var popularMovieResposne = MutableLiveData<List<Movie>>()


    fun getPopularMovie(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val movieResponse = networkSDK.fetchPopularMovies()
                 popularMovieArrayList=movieResponse.movies
                popularMovieResposne.postValue(popularMovieArrayList)
                Log.d("popMovies MovieResponse", popularMovieArrayList.toString())

            } catch (e: Exception) {
                Log.e("API", "Failed to fetch popular movies", e)
                errorDialogConfig.postValue(e.message ?: "An unknown error occurred.")
//                showErrorDialog(this, e.message ?: "An unknown error occurred.")

            }
        }
    }
}