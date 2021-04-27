package ru.yellowshark.favoritemovies.domain.mapper

import ru.yellowshark.favoritemovies.data.db.entity.MovieEntity
import ru.yellowshark.favoritemovies.domain.model.Movie
import ru.yellowshark.favoritemovies.utils.Mapper

class LocalMapper : Mapper<MovieEntity, Movie> {
    override fun toDomain(dto: MovieEntity): Movie =
        Movie(
            id = dto.id,
            title = dto.title,
            desc = dto.desc,
            image = dto.image,
            releaseDate = dto.releaseDate,
            isLiked = dto.isLiked
        )

    override fun fromDomain(domain: Movie): MovieEntity =
        MovieEntity(
            id = domain.id,
            title = domain.title,
            desc = domain.desc,
            image = domain.image,
            releaseDate = domain.releaseDate,
            isLiked = domain.isLiked
        )
}