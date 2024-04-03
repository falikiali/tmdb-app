package com.falikiali.tmdbapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.falikiali.tmdbapp.data.local.dao.MediaDao
import com.falikiali.tmdbapp.data.paging.ListMediaPagingSource
import com.falikiali.tmdbapp.data.paging.SearchMediaPagingSource
import com.falikiali.tmdbapp.data.paging.SimilarMediaPagingSource
import com.falikiali.tmdbapp.data.remote.ApiService
import com.falikiali.tmdbapp.data.remote.dto.ErrorResponse
import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.domain.model.MediaDetail
import com.falikiali.tmdbapp.domain.repository.MediaRepository
import com.falikiali.tmdbapp.helper.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ImplMediaRepository @Inject constructor(private val apiService: ApiService, private val mediaDao: MediaDao): MediaRepository {
    /**
     * Remote
     */
    override suspend fun getMediaForHome(): Flow<ResultState<List<List<Media>>>> {
        return flow {
            emit(ResultState.Loading)
            try {
                val resultTrendingMovie = apiService.getListTrendingMedia("movie", 1)
                val resultNowPlayingMovie = apiService.getListMedia("movie", "now_playing", 1)
                val resultUpcomingMovie = apiService.getListMedia("movie", "upcoming", 1)
                val resultTrendingTv = apiService.getListTrendingMedia("tv", 1)
                val resultAiringTodayTv = apiService.getListMedia("tv", "airing_today", 1)

                val listResult = mutableListOf<List<Media>>()
                listResult.add(resultTrendingMovie.results.take(10).map { it.toDomain() })
                listResult.add(resultNowPlayingMovie.results.take(10).map { it.toDomain() })
                listResult.add(resultUpcomingMovie.results.take(10).map { it.toDomain() })
                listResult.add(resultTrendingTv.results.take(10).map { it.toDomain() })
                listResult.add(resultAiringTodayTv.results.take(10).map { it.toDomain() })

                emit(ResultState.Success(listResult))
            } catch (e: HttpException) {
                e.response()?.errorBody().let {
                    val err = Gson().fromJson(it?.charStream(), ErrorResponse::class.java)
                    emit(ResultState.Failed(err.statusMessage, e.code()))
                }
            } catch (e: IOException) {
                emit(ResultState.Failed("No connection, check your internet please", 0))
            } catch (e: Exception) {
                emit(ResultState.Failed(e.message.toString(), 0))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getListMedia(
        mediaType: String,
        mediaCategory: String
    ): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, initialLoadSize = 10, prefetchDistance = 1
            ),
            pagingSourceFactory = {
                ListMediaPagingSource(apiService, mediaType, mediaCategory)
            }
        )
            .flow
            .flowOn(Dispatchers.IO)
    }

    override suspend fun searchMedia(query: String): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, initialLoadSize = 10, prefetchDistance = 1
            ),
            pagingSourceFactory = {
                SearchMediaPagingSource(apiService, if (query == "") "a" else query)
            }
        )
            .flow
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getDetailMedia(
        mediaType: String,
        mediaId: Int
    ): Flow<ResultState<MediaDetail>> {
        return flow {
            emit(ResultState.Loading)
            try {
                val responseDetailMedia = apiService.getDetailMedia(mediaType, mediaId)
                val responseSimilarMedia = apiService.getSimilarMedia(mediaType, mediaId, 1)

                val result = responseDetailMedia.toDomainDetail(responseSimilarMedia.results)
                emit(ResultState.Success(result))
            } catch (e: HttpException) {
                e.response()?.errorBody().let {
                    val err = Gson().fromJson(it?.charStream(), ErrorResponse::class.java)
                    emit(ResultState.Failed(err.statusMessage, e.code()))
                }
            } catch (e: IOException) {
                emit(ResultState.Failed("No connection, check your internet please", 0))
            } catch (e: Exception) {
                emit(ResultState.Failed(e.message.toString(), 0))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getSimilarMedia(mediaType: String, mediaId: Int): Flow<PagingData<Media>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, initialLoadSize = 10, prefetchDistance = 1
            ),
            pagingSourceFactory = {
                SimilarMediaPagingSource(apiService, mediaType, mediaId)
            }
        )
            .flow
            .flowOn(Dispatchers.IO)
    }

    /**
     * Local database
     */
    override suspend fun addBookmarkMedia(media: Media) {
        mediaDao.insertMedia(media.toEntity())
    }

    override suspend fun removeBookmarkMedia(media: Media) {
        mediaDao.deleteMedia(media.toEntity())
    }

    override suspend fun statusBookmarkMedia(mediaId: Int): Flow<Boolean> {
        return mediaDao.statusBookmarkMedia(mediaId)
    }

    @OptIn(FlowPreview::class)
    override suspend fun getAndSearchBookmarkMedia(type: String, query: String): Flow<PagingData<Media>> {
        if (type != "all" && query != "") {
            return Pager(
                config = PagingConfig(
                    pageSize = 10, initialLoadSize = 10, prefetchDistance = 1
                ),
                pagingSourceFactory = {
                    mediaDao.searchMediaByType(type, "%$query%")
                }
            )
                .flow
                .flowOn(Dispatchers.IO)
                .debounce(500L)
                .map {
                    it.map { entity -> entity.toDomain() }
                }
        }

        if (type == "all" && query != "") {
            return Pager(
                config = PagingConfig(
                    pageSize = 10, initialLoadSize = 10, prefetchDistance = 1
                ),
                pagingSourceFactory = {
                    mediaDao.searchMedia("%$query%")
                }
            )
                .flow
                .flowOn(Dispatchers.IO)
                .debounce(500L)
                .map {
                    it.map { entity -> entity.toDomain() }
                }
        }

        if (type != "all") {
            return Pager(
                config = PagingConfig(
                    pageSize = 10, initialLoadSize = 10, prefetchDistance = 1
                ),
                pagingSourceFactory = {
                    mediaDao.getAllMediaByType(type)
                }
            )
                .flow
                .flowOn(Dispatchers.IO)
                .map {
                    it.map { entity -> entity.toDomain() }
                }
        }

        return Pager(
            config = PagingConfig(
                pageSize = 10, initialLoadSize = 10, prefetchDistance = 1
            ),
            pagingSourceFactory = {
                mediaDao.getAllMedia()
            }
        )
            .flow
            .flowOn(Dispatchers.IO)
            .map {
                it.map { entity -> entity.toDomain() }
            }
    }

}