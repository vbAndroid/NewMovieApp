package com.example.movielistapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.movielistapp.databinding.MovieListItemBinding
import com.example.networksdk.Movie
import java.util.ArrayList

class MoviesAdapter(
    val context: Context,
    val arrayList: ArrayList<Movie>,
    private var itemClickListener: MovieItemClickListener? = null
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieData: Movie = arrayList.get(position)
        val radius = 10
        holder.binding.textViewMovieName.text = movieData.title
        holder.binding.textViewMovieDate.text=movieData.releaseDate
        holder.binding.progressText.text = getPopularityPercentage(movieData.popularity).toString().plus("%")
        holder.binding.progressBar.progress=getPopularityPercentage(movieData.popularity)

        Glide.with(MyApplication.myApplication.applicationContext)
            .load("https://image.tmdb.org/t/p/w185/"+movieData.posterPath)
            .placeholder(R.drawable.movie1) // Replace with your placeholder
            .apply(RequestOptions().transform(RoundedCorners(radius)))
            .error(R.drawable.movie1)
            .into(holder.binding.imageViewMovie)


        holder.itemView.setOnClickListener {
            itemClickListener?.movieItemClickListener(
                movieData.id,
                arrayList[position],
                position
            )
        }

    }

    class MovieViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return arrayList.size
    }
}

