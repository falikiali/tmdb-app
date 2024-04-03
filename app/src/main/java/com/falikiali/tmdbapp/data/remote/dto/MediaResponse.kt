package com.falikiali.tmdbapp.data.remote.dto

import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.domain.model.MediaDetail
import com.google.gson.annotations.SerializedName

data class MediaResponse(

    @field:SerializedName("page") val page: Int,
    @field:SerializedName("total_pages") val totalPages: Int,
    @field:SerializedName("results") val results: List<ItemMediaResponse>,
    @field:SerializedName("total_results") val totalResults: Int

)

data class ItemMediaResponse(

    @field:SerializedName("title") val title: String? = null,
    @field:SerializedName("name") val name: String? = null,
    @field:SerializedName("poster_path") val posterPath: String? = null,
    @field:SerializedName("release_date") val releaseDate: String? = null,
    @field:SerializedName("first_air_date") val firstAirDate: String? = null,
    @field:SerializedName("id") val id: Int? = null,
    @field:SerializedName("media_type") val mediaType: String? = null,
    @field:SerializedName("vote_average") val voteAverage: Double? = null,
    @field:SerializedName("tagline") val tagline: String? = null,
    @field:SerializedName("backdrop_path") val backdropPath: String? = null,
    @field:SerializedName("overview") val overview: String? = null,
    @field:SerializedName("genres") val listGenres: List<ItemGenreResponse>? = null,
    @field:SerializedName("adult") val adult: Boolean? = null

) {
    fun toDomain(): Media {
        return Media(
            title = title,
            name = name,
            posterPath = posterPath,
            releaseDate = releaseDate,
            firstAirDate = firstAirDate,
            id = id ?: -1,
            mediaType = mediaType,
            voteAverage = voteAverage
        )
    }

    fun toDomainDetail(similarMedia: List<ItemMediaResponse>): MediaDetail {
        return MediaDetail(
            title = title,
            name = name,
            posterPath = posterPath,
            backdropPath = backdropPath,
            releaseDate = releaseDate,
            firstAirDate = firstAirDate,
            id = id ?: -1,
            voteAverage = voteAverage,
            overview = overview,
            tagline = tagline,
            genres = listGenres?.map { it.name },
            adult = adult,
            similarMedia = similarMedia.take(5).map {
                it.toDomain()
            }
        )
    }
}

data class ItemGenreResponse(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String
)
