package com.smmousavi.i_feature.movies.impl.xml.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.smmousavi.i_core.designsystem.databinding.ItemMovieCardBinding
import com.smmousavi.i_core.model.movies.movie.MovieModel

class MoviesListAdapter(
    private val onMovieClick: (MovieModel) -> Unit,
    private val onFavoriteClick: (MovieModel) -> Unit,
) : ListAdapter<MovieModel, MoviesViewHolder>(MovieDiffCallback) {

    init {
        setHasStableIds(true)
        // prevent scrolling to the wrong position when the data list is empty
        // mostly happens when restoring item position after rotation
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun getItemId(position: Int): Long = getItem(position)
        .id
        .hashCode()
        .toLong()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MoviesViewHolder {
        val binding = ItemMovieCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return MoviesViewHolder(
            binding,
            onMovieClick,
            onFavoriteClick,
        )
    }

    override fun onBindViewHolder(
        holder: MoviesViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}