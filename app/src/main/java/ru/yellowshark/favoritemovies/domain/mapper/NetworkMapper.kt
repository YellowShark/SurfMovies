package ru.yellowshark.favoritemovies.domain.mapper

import ru.yellowshark.favoritemovies.data.network.response.MovieResult
import ru.yellowshark.favoritemovies.domain.model.Movie
import ru.yellowshark.favoritemovies.utils.Mapper

class NetworkMapper : Mapper<MovieResult, Movie> {
    override fun toDomain(dto: MovieResult) =
        Movie(
            id = dto.id,
            title = dto.title,
            desc = dto.overview,
            image = dto.posterPath ?: "",
            releaseDate = dto.releaseDate,
            isLiked = false
        )

    override fun fromDomain(domain: Movie): MovieResult {
        //we don't need that because we will not post any result
        TODO("Not yet implemented")
    }
}