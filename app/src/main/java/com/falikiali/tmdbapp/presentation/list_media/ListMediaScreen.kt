package com.falikiali.tmdbapp.presentation.list_media

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.falikiali.tmdbapp.component.PagingMediaCard
import com.falikiali.tmdbapp.helper.Screens

@Composable
fun ListMediaScreen(
    modifier: Modifier = Modifier,
    mediaType: String,
    mediaCategory: String,
    listMediaState: ListMediaState,
    onEvent: (ListMediaUiEvent) -> Unit,
    navController: NavHostController
) {
    val listMedia = listMediaState.listMedia?.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        onEvent(ListMediaUiEvent.LoadListMedia(mediaType = mediaType, mediaCategory = mediaCategory))
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (listMedia != null) {
                PagingMediaCard(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    mediaSearch = listMedia,
                    onClick = { media ->
                        navController.navigate(Screens.Detail.route + "/$mediaType/${media.id}")
                    }
                )
            }
        }

        FilledTonalIconButton(
            modifier = Modifier
                .padding(start = 8.dp, top = 48.dp)
                .align(Alignment.TopStart),
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "back button")
        }
    }
}