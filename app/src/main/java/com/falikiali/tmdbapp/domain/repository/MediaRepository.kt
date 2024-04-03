package com.falikiali.tmdbapp.domain.repository

import androidx.paging.PagingData
import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.domain.model.MediaDetail
import com.falikiali.tmdbapp.helper.ResultState
import kotlinx.coroutines.flow.Flow

interface MediaRepository {

    suspend fun getMediaForHome(): Flow<ResultState<List<List<Media>>>>
    suspend fun getListMedia(mediaType: String, mediaCategory: String): Flow<PagingData<Media>>
    suspend fun searchMedia(query: String): Flow<PagingData<Media>>
    suspend fun getDetailMedia(mediaType: String, mediaId: Int): Flow<ResultState<MediaDetail>>
    suspend fun getSimilarMedia(mediaType: String, mediaId: Int): Flow<PagingData<Media>>

    suspend fun addBookmarkMedia(media: Media)
    suspend fun removeBookmarkMedia(media: Media)
    suspend fun statusBookmarkMedia(mediaId: Int): Flow<Boolean>
    suspend fun getAndSearchBookmarkMedia(type: String, query: String): Flow<PagingData<Media>>

}