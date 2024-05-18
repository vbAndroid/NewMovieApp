package com.example.movielistapp.mvvm.views.fragment.popularMovieFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movielistapp.MovieItemClickListener
import com.example.movielistapp.MoviesAdapter
import com.example.movielistapp.mvvm.views.fragment.latestMovieFragment.LatestMoviesVM
import com.example.movielistapp.R
import com.example.networksdk.Movie
import com.test.networksdk.NetworkSDK
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    val viewModel: PopularMoviesVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular_movies, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewPopularMovies)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // Set 2 columns
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mNetworkErrorData.observe(viewLifecycleOwner) {
            Log.e("INTERNET", "is internet available? - ${if (it) "Yes" else "No"}")
            if (!it) {
                Toast.makeText(context, "Please connect internet", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getPopularMovie()
        bindObservers()

    }

    private fun bindObservers() {
        viewModel.errorDialogConfig.observe(this){
            if (it.isNotEmpty()){
                context?.let { it1 -> showErrorDialog(it1,it) }
                viewModel.errorDialogConfig.postValue("")
            }
        }
        viewModel.popularMovieResposne.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                val popularMoviesAdapter = context?.let {
                    MoviesAdapter(
                        it,
                        viewModel.popularMovieArrayList as ArrayList<Movie>,
                        object : MovieItemClickListener {
                            override fun movieItemClickListener(
                                movieId: Long,
                                movie: Movie,
                                position: Int
                            ) {
                                val action =
                                    PopularMoviesFragmentDirections.actionPopularMovieFragmentToMovieDetailsFragment(
                                        movie.id
                                    )
                                findNavController().navigate(action)
                            }
                        }
                    )
                }
                recyclerView.adapter = popularMoviesAdapter

            }
        }
    }
    fun showErrorDialog(context: Context, errorMessage: String) {
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

}
