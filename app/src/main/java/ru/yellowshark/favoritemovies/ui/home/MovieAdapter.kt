package ru.yellowshark.favoritemovies.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.yellowshark.favoritemovies.domain.model.Movie

class MovieAdapter(
    val onItemClickListener: ((Movie) -> Unit),
    val onLikeClickListener: ((Movie, Int) -> Unit)
) : RecyclerView.Adapter<MovieViewHolder>() {
    var movies: List<Movie> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder.create(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(position, movies[position], onItemClickListener, onLikeClickListener)
    }

    override fun getItemCount() = movies.size

    fun updateLike(pos: Int) {
        val isLiked = movies[pos].isLiked
        movies[pos].isLiked = !isLiked
        notifyItemChanged(pos)
    }
}