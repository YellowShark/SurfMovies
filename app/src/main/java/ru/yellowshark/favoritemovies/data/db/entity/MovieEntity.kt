package ru.yellowshark.favoritemovies.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_local_movies")
data class MovieEntity(
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
