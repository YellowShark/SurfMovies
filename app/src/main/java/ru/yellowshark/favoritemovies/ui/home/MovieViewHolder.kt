package ru.yellowshark.favoritemovies.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.yellowshark.favoritemovies.R
import ru.yellowshark.favoritemovies.databinding.ItemMovieBinding
import ru.yellowshark.favoritemovies.domain.model.Movie
import ru.yellowshark.favoritemovies.utils.IMAGE_PREFIX_URL

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMovieBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding)
        }
    }

    fun bind(
        position: Int,
        movie: Movie,
        onItemClickListener: (Movie) -> Unit,
        onLikeClickListener: (Movie, Int) -> Unit
    ) {
        with(binding) {
            Glide.with(root.context)
                .load("$IMAGE_PREFIX_URL${movie.image}")
                .placeholder(R.drawable.ic_local_movies)
                .fitCenter()
                .into(moviePosterIv)

            movieTitleTv.text = movie.title
            movieDateTv.text = movie.releaseDate
            movieDescTv.text = movie.desc
            movieLikeBtn.setImageDrawable(
                ContextCompat.getDrawable(
                    root.context,
                    if (!movie.isLiked) R.drawable.ic_heart
                    else
                        R.drawable.ic_heart_fill
                )
            )

            root.setOnClickListener { onItemClickListener(movie) }
            movieLikeBtn.setOnClickListener {
                val isLiked = movie.isLiked
                movie.isLiked = !isLiked
                onLikeClickListener(movie, position)
            }
        }
    }
}