package ru.yellowshark.favoritemovies.domain.model

class Movie(
    val title: String,
    val desc: String,
    val genreIds: List<Int>,
    val image: String,
    val releaseDate: String,
    val isAdult: Boolean
)