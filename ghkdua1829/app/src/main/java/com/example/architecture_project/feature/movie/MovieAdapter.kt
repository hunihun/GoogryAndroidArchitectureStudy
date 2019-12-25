package com.example.architecture_project.feature.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.architecture_project.R
import com.example.architecture_project.data.Movie

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {
    private var movieData:ArrayList<Movie> = ArrayList()

    fun setMovieItemList(movieData:ArrayList<Movie>){
        this.movieData=movieData
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)

        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieData[position])
    }
}