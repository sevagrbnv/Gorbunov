package ru.sevagrbnv.cinemaapp.presentation.recyclerView

import androidx.recyclerview.widget.DiffUtil
import ru.sevagrbnv.cinemaapp.domain.Movie

class MovieDiffCallback: DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
}