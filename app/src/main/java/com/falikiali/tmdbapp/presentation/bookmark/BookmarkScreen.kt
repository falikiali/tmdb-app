package com.falikiali.tmdbapp.presentation.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.falikiali.tmdbapp.component.PagingMediaCard
import com.falikiali.tmdbapp.component.SearchBar
import com.falikiali.tmdbapp.helper.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    bookmarkState: BookmarkState,
    onEvent: (BookmarkUiEvent) -> Unit,
    navController: NavHostController
) {
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    val bookmarkMedia = bookmarkState.bookmarkMedia?.collectAsLazyPagingItems()

    LaunchedEffect(key1 = bookmarkState.query, key2 = bookmarkState.filter) {
        onEvent(BookmarkUiEvent.OnLoadBookmarkMedia)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            FilledTonalIconButton(
                modifier = Modifier
                    .size(56.dp)
                    .padding(start = 16.dp),
                onClick = {
                    isSheetOpen = true
                }
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Filled.FilterAlt,
                    contentDescription = "filter"
                )
            }

            SearchBar(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .fillMaxWidth(),
                text = bookmarkState.query,
                onValueChange = { onEvent(BookmarkUiEvent.OnSearchQueryChange(it)) }
            ) {
                onEvent(BookmarkUiEvent.OnLoadBookmarkMedia)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (bookmarkMedia != null) {
            PagingMediaCard(mediaSearch = bookmarkMedia, onClick = { media ->
                navController.navigate(Screens.Detail.route + "/${media.mediaType}/${media.id}")
            })
        }

        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { isSheetOpen = false }
            ) {
                BottomSheetContent(bookmarkState = bookmarkState, onEvent = onEvent)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    bookmarkState: BookmarkState,
    onEvent: (BookmarkUiEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Filter",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                onClick = { onEvent(BookmarkUiEvent.OnFilterChange(filter = "all")) },
                label = {
                    Text("All")
                },
                selected = bookmarkState.filter == "all"
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilterChip(
                onClick = { onEvent(BookmarkUiEvent.OnFilterChange(filter = "movie")) },
                label = {
                    Text("Movie")
                },
                selected = bookmarkState.filter == "movie"
            )

            Spacer(modifier = Modifier.width(8.dp))

            FilterChip(
                onClick = { onEvent(BookmarkUiEvent.OnFilterChange(filter = "tv")) },
                label = {
                    Text("Tv Series")
                },
                selected = bookmarkState.filter == "tv"
            )
        }
    }
}