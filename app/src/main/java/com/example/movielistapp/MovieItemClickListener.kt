package com.example.movielistapp

import android.view.View
import com.example.networksdk.Movie


interface MovieItemClickListener {

    fun movieItemClickListener(movieId: Long,movie: Movie, position: Int) {}


}