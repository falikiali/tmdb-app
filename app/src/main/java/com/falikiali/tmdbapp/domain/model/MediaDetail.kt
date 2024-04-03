package com.falikiali.tmdbapp.domain.model

data class MediaDetail(
    val title: String? = null,
    val name: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val firstAirDate: String? = null,
    val id: Int,
    val voteAverage: Double? = null,
    val genres: List<String>? = null,
    val overview: String? = null,
    val tagline: String? = null,
    val adult: Boolean? = null,
    val similarMedia: List<Media>? = null
)
