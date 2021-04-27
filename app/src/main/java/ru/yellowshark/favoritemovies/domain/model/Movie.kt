package ru.yellowshark.favoritemovies.domain.model

class Movie(
    val id: Int,
    val title: String,
    val desc: String,
    val image: String,
    val releaseDate: String,
    var isLiked: Boolean
)