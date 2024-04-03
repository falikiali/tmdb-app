package com.falikiali.tmdbapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.falikiali.tmdbapp.component.ErrorScreen
import com.falikiali.tmdbapp.component.MediaCard
import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.helper.Screens

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    onEvent: (HomeUiEvent) -> Unit,
    navController: NavHostController
) {
    LaunchedEffect(key1 = true) {
        onEvent(HomeUiEvent.LoadMedia)
    }

    LaunchedEffect(key1 = homeState.isRefresh) {
        if (homeState.isRefresh) {
            onEvent(HomeUiEvent.LoadMedia)
        }
    }

    if (homeState.media != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            MediaListInHome(
                navController = navController,
                mediaList = homeState.media[1],
                title = "Movie - Now Playing",
                type = "movie",
                category = "now_playing"
            )

            Spacer(modifier = Modifier.height(16.dp))

            MediaListInHome(
                navController = navController,
                mediaList = homeState.media[2],
                title = "Movie - Upcoming",
                type = "movie",
                category = "upcoming"
            )

            Spacer(modifier = Modifier.height(16.dp))

            MediaListInHome(
                navController = navController,
                mediaList = homeState.media[4],
                title = "Tv Series - Airing Today",
                type = "tv",
                category = "airing_today"
            )
        }
    }

    if (homeState.isError != null) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            ErrorScreen(
                modifier = Modifier
                    .align(Alignment.Center),
                messageError = homeState.isError
            ) {
                onEvent(HomeUiEvent.Refresh)
            }
        }
    }
}

@Composable
fun MediaListInHome(
    navController: NavHostController,
    mediaList: List<Media>,
    title: String,
    type: String,
    category: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )

        Text(
            modifier = Modifier
                .clickable {
                    navController.navigate(Screens.List.route + "/$type/$category")
                },
            text = "See All",
            fontWeight = FontWeight.Thin,
            fontSize = 16.sp
        )
    }

    Spacer(modifier = Modifier.height(6.dp))

    LazyRow(
        modifier = Modifier
    ) {
        items(mediaList.size) {
            MediaCard(media = mediaList[it], onClick = {
                navController.navigate(Screens.Detail.route + "/$type/${mediaList[it].id}")
            })
        }
    }
}