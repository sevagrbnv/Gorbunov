package ru.sevagrbnv.cinemaapp.presentation.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.databinding.MovieBinding
import ru.sevagrbnv.cinemaapp.domain.Movie


class MovieAdapter : ListAdapter<Movie, MovieViewHolder>(
    MovieDiffCallback()
) {
    var onItemClickListener: ((Movie) -> Unit)? = null
    var onItemLongClickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.movie,
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        var item = getItem(position)

        when (val binding = holder.binding) {
            is MovieBinding -> {
                with(binding) {
                    Picasso.get()
                        .load(item.posterUrl)
                        .into(this.imagePoster)
                    textTitle.text = item.name
                    textYear.text = item.year
                    imageStar.isVisible = item.isLiked
                    root.setOnClickListener {
                        onItemClickListener?.invoke(item)
                    }
                    root.setOnLongClickListener {
                        onItemLongClickListener?.invoke(item)
                        item = item.copy(isLiked = !item.isLiked)
                        true
                    }
                }
            }
        }
    }
}