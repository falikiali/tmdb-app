package com.falikiali.tmdbapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.falikiali.tmdbapp.domain.model.Media

@Entity(tableName = "medias")
data class MediaEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("poster") val poster: String,
    @ColumnInfo("rating") val rating: Double
) {
    fun toDomain(): Media {
        if (type == "tv") {
            return Media(
                id = id,
                posterPath = poster,
                voteAverage = rating,
                mediaType = type,
                name = title
            )
        }

        return Media(
            id = id,
            posterPath = poster,
            voteAverage = rating,
            mediaType = type,
            title = title
        )
    }
}
