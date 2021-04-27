package ru.yellowshark.favoritemovies.domain.mapper

import ru.yellowshark.favoritemovies.data.network.response.MovieResult
import ru.yellowshark.favoritemovies.domain.model.Movie
import ru.yellowshark.favoritemovies.utils.Mapper
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private fun String.toRussianFormat():String {
    var sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var date: Date? = null
    try {
        date = sdf.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    var millis: Long = 0
    if (date != null) {
        millis = date.time
    }
    sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(Date(millis))
}

class NetworkMapper : Mapper<MovieResult, Movie> {
    override fun toDomain(dto: MovieResult) =
        Movie(
            id = dto.id,
            title = dto.title,
            desc = dto.overview,
            image = dto.posterPath ?: "",
            releaseDate = dto.releaseDate.toRussianFormat(),
            isLiked = false
        )

    override fun fromDomain(domain: Movie): MovieResult {
        //we don't need that because we will not post any result
        TODO("Not yet implemented")
    }
}