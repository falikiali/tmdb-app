package com.falikiali.tmdbapp.data.remote

import com.falikiali.tmdbapp.data.remote.dto.ItemMediaResponse
import com.falikiali.tmdbapp.data.remote.dto.MediaResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("trending/{mediaType}/week")
    suspend fun getListTrendingMedia(
        @Path("mediaType") mediaType: String,
        @Query("page") page: Int
    ): MediaResponse

    @GET("{mediaType}/{mediaCategory}")
    suspend fun getListMedia(
        @Path("mediaType") mediaType: String,
        @Path("mediaCategory") mediaCategory: String,
        @Query("page") page: Int
    ): MediaResponse

    @GET("{mediaType}/{mediaId}")
    suspend fun getDetailMedia(
        @Path("mediaType") mediaType: String,
        @Path("mediaId") mediaId: Int
    ): ItemMediaResponse

    @GET("{mediaType}/{mediaId}/similar")
    suspend fun getSimilarMedia(
        @Path("mediaType") mediaType: String,
        @Path("mediaId") mediaId: Int,
        @Query("page") page: Int
    ): MediaResponse

    @GET("search/multi")
    suspend fun searchMedia(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MediaResponse

}