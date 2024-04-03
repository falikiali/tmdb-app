package com.falikiali.tmdbapp.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.falikiali.tmdbapp.domain.model.Media

@Composable
fun PagingMediaCard(
    modifier: Modifier = Modifier,
    mediaSearch: LazyPagingItems<Media>,
    onClick: (Media) -> Unit
) {
    if (mediaSearch.loadState.refresh is LoadState.Loading) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
        ) {
            items(6) {
                ShimmerCard()
            }
        }
    }

    if (mediaSearch.loadState.refresh is LoadState.NotLoading) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
        ) {
            items(mediaSearch.itemCount) {
                mediaSearch[it]?.let { media ->
                    MediaCard(media = media, onClick = { onClick(media) })
                }
            }

            if (mediaSearch.loadState.append is LoadState.Loading) {
                items(2) {
                    ShimmerCard()
                }
            }
        }
    }

    if (mediaSearch.loadState.refresh is LoadState.Error) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ErrorScreen(
                modifier = Modifier.align(Alignment.Center),
                messageError = "Oops, Something went wrong!"
            ) {
                mediaSearch.refresh()
            }
        }
    }
}