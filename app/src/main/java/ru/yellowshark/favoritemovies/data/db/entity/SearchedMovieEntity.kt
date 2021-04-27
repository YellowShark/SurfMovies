package ru.yellowshark.favoritemovies.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.yellowshark.favoritemovies.data.db.TABLE_SEARCHED_MOVIES

@Entity(tableName = TABLE_SEARCHED_MOVIES)
data class SearchedMovieEntity(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "desc")
    var desc: String,
    @ColumnInfo(name = "image")
    var image: String,
    @ColumnInfo(name = "release_date")
    var releaseDate: String,
    @ColumnInfo(name = "is_liked")
    var isLiked: Boolean
)
