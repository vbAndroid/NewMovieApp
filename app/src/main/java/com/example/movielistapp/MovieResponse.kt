package com.example.movielistapp

import com.example.networksdk.*
import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class MovieResponse(
    @SerializedName("results")
    val movies: List<Movie1>
)

data class Movie1(
    val id: Long,
    val title: String,
    val original_title:String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int

)




fun getPopularityPercentage(popularity: Double, maxPopularity: Double = 10000.0): Int {
    return ((popularity / maxPopularity) * 100).roundToInt()
}