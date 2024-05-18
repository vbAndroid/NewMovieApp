package com.example.movielistapp.mvvm.views.fragment.movieDetailsFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movielistapp.*
import com.example.movielistapp.databinding.ActivityMainBinding
import com.example.movielistapp.databinding.MovieDetailsFragmentBinding
import com.example.networksdk.Movie
import com.test.networksdk.NetworkSDK
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoviesAdapter
    val viewModel: MovieDetailsVM by viewModels()
    private lateinit var binding: MovieDetailsFragmentBinding
    private val args: MovieDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieName.text = "The Great Movie"
        Log.d("MovieDetails", "Movie ID:${args.movieID}")
        viewModel.getMovieDetails(args.movieID.toInt())

        bindObservers()

    }

    fun bindObservers() {
        viewModel.detailsResposne.observe(viewLifecycleOwner) {

            binding.movieName.text = it.title
            binding.textReleaseDate.text = it.release_date.plus("${it.origin_country.toString()}")
            binding.textMovieType.text = it.genres.joinToString(separator = ", ") { it.name }

            val radius = 5
            Glide.with(MyApplication.myApplication.applicationContext)
                .load("https://image.tmdb.org/t/p/w185/" + it.poster_path)
                .apply(RequestOptions().transform(RoundedCorners(radius)))
                .error(R.drawable.movie1)
                .into(binding.movieImage)

            binding.textMovieOverview.text = it.overview
            binding.progressText.text = getPopularityPercentage(it.popularity).toString().plus("%")
            binding.progressBar.progress=getPopularityPercentage(it.popularity)


        }
    }

    fun setPopularMovie() {
//            val popularmoviesList = mutableListOf<com.example.networksdk.MovieResponse>()
//            popularmoviesList.add(popularMovies)
        val popularMoviesAdapter = context?.let {
            MoviesAdapter(
                it,
                viewModel.popularMovieArrayList as ArrayList<Movie>
            )
        }
        recyclerView.adapter = popularMoviesAdapter
//            Log.d("latestMovies MovieResponse", popularmoviesList.toString())


    }


//    @SuppressLint("SuspiciousIndentation")
//    fun getMovieDetails(){
//        CoroutineScope(Dispatchers.IO).launch{
//            val moviesData = networkSDK.getMovieDetails(1290833)
//            moviesData.enqueue(object : Callback<MovieDetails>{
//                override fun onResponse(
//                    call: Call<MovieDetails>,
//                    response: Response<MovieDetails>
//                ) {
//                    if (response.isSuccessful) {
//                        val movieResponse = response.body()
//                        movieResponse?.let {
//                            val movies = it.title
//                            Log.d("latestMoviesDetails", "Title: ${movies}")
//                        }
//                    }
//                }
//                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//            }
//
//            )
//
//        }
//
//    }
}
