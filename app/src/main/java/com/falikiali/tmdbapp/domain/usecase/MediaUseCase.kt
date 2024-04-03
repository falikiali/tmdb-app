package com.falikiali.tmdbapp.domain.usecase

import com.falikiali.tmdbapp.domain.usecase.media.AddBookmarkMedia
import com.falikiali.tmdbapp.domain.usecase.media.GetAndSearchBookmarkMedia
import com.falikiali.tmdbapp.domain.usecase.media.GetDetailMedia
import com.falikiali.tmdbapp.domain.usecase.media.GetListMedia
import com.falikiali.tmdbapp.domain.usecase.media.GetMediaForHome
import com.falikiali.tmdbapp.domain.usecase.media.GetSimilarMedia
import com.falikiali.tmdbapp.domain.usecase.media.GetStatusMediaFavorite
import com.falikiali.tmdbapp.domain.usecase.media.RemoveBookmarkMedia
import com.falikiali.tmdbapp.domain.usecase.media.SearchMedia
import javax.inject.Inject

data class MediaUseCase @Inject constructor(
    val getMediaForHome: GetMediaForHome,
    val getListMedia: GetListMedia,
    val searchMedia: SearchMedia,
    val getDetailMedia: GetDetailMedia,
    val getSimilarMedia: GetSimilarMedia,
    val addBookmarkMedia: AddBookmarkMedia,
    val removeBookmarkMedia: RemoveBookmarkMedia,
    val getStatusMediaBookmark: GetStatusMediaFavorite,
    val getAllBookmarkMedia: GetAndSearchBookmarkMedia
)
