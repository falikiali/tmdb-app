package com.falikiali.tmdbapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.falikiali.tmdbapp.data.remote.ApiService
import com.falikiali.tmdbapp.domain.model.Media

class SearchMediaPagingSource(private val apiService: ApiService, private val query: String): PagingSource<Int, Media>() {
    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        return try {
            val page = params.key ?: 1
            val response = apiService.searchMedia(query, page)

            LoadResult.Page(
                data = response.results.filter { it.mediaType == "tv" || it.mediaType == "movie" }.map { it.toDomain() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page == response.totalPages) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}