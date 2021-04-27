package ru.yellowshark.favoritemovies.domain.mapper

import ru.yellowshark.favoritemovies.data.db.entity.SearchedMovieEntity
import ru.yellowshark.favoritemovies.domain.model.Movie
import ru.yellowshark.favoritemovies.utils.Mapper

class LocalSearchMapper : Mapper<SearchedMovieEntity, Movie> {
    override fun toDomain(dto: SearchedMovieEntity): Movie =
        Movie(
            id = dto.id,
            title = dto.title,
            desc = dto.desc,
            image = dto.image,
            releaseDate = dto.releaseDate,
            isLiked = dto.isLiked
        )

    override fun fromDomain(domain: Movie): SearchedMovieEntity =
        SearchedMovieEntity(
            id = domain.id,
            title = domain.title,
            desc = domain.desc,
            image = domain.image,
            releaseDate = domain.releaseDate,
            isLiked = domain.isLiked
        )
}