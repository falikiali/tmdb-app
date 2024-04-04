package com.falikiali.tmdbapp.domain.model

import com.falikiali.tmdbapp.data.local.entity.MediaEntity

data class Media(
    val title: String? = null,
    val name: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val firstAirDate: String? = null,
    val id: Int,
    val mediaType: String? = null,
    val voteAverage: Double? = null
) {
    fun toEntity(): MediaEntity {
        return MediaEntity(
            id = id,
            poster = posterPath ?: "",
            type = mediaType ?: "Unknown",
            rating = voteAverage ?: 0.0,
            title = (title ?: name) ?: "Unknown"
        )
    }
}
