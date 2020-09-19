package com.hong.architecturestudy.ui.main.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hong.architecturestudy.R
import com.hong.architecturestudy.data.model.MovieData
import com.hong.architecturestudy.ui.main.holder.MovieViewHolder

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>() {
    private val items = mutableListOf<MovieData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_movie_list, parent, false
            )
        ).apply {
            itemView.setOnClickListener {
                val item = items[absoluteAdapterPosition]
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                itemView.context.startActivity(intent)
            }
        }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(item: List<MovieData>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }
}