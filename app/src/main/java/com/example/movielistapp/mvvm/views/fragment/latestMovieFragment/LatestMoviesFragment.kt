package com.example.movielistapp.mvvm.views.fragment.latestMovieFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movielistapp.MoviesAdapter
import com.example.movielistapp.MyApplication
import com.example.movielistapp.R
import com.example.movielistapp.databinding.MovieDetailsFragmentBinding
import com.example.movielistapp.getPopularityPercentage
import com.example.networksdk.Movie
import com.test.networksdk.NetworkSDK
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class LatestMoviesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MoviesAdapter
    val viewModel: LatestMoviesVM by viewModels()
    private lateinit var binding: MovieDetailsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLatestMovie()
        bindObservers()


    }
  fun bindObservers(){
      viewModel.latestMovieResposne.observe(viewLifecycleOwner){
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
          binding.progressBar.progress= getPopularityPercentage(it.popularity)      }
  }

}
