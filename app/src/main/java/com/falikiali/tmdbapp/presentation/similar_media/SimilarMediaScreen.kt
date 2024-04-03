package com.falikiali.tmdbapp.presentation.similar_media

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.falikiali.tmdbapp.component.PagingMediaCard
import com.falikiali.tmdbapp.helper.Screens

@Composable
fun SimilarMediaScreen(
    modifier: Modifier = Modifier,
    mediaType: String,
    mediaId: Int,
    similarMediaState: SimilarMediaState,
    onEvent: (SimilarMediaUiEvent) -> Unit,
    navController: NavHostController
) {
    val similarMedia = similarMediaState.similarMedia?.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        onEvent(SimilarMediaUiEvent.LoadSimilarMedia(mediaType = mediaType, mediaId = mediaId))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (similarMedia != null) {
                PagingMediaCard(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    mediaSearch = similarMedia,
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