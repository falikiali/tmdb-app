package com.falikiali.tmdbapp.presentation.home

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.falikiali.tmdbapp.BuildConfig
import com.falikiali.tmdbapp.component.ErrorScreen
import com.falikiali.tmdbapp.component.MediaCard
import com.falikiali.tmdbapp.component.ShimmerCard
import com.falikiali.tmdbapp.component.shimmerEffect
import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.helper.Screens
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    onEvent: (HomeUiEvent) -> Unit,
    navController: NavHostController
) {
    val ctx = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = true) {
        onEvent(HomeUiEvent.LoadMedia)
    }

    LaunchedEffect(key1 = homeState.isRefresh) {
        if (homeState.isRefresh) {
            onEvent(HomeUiEvent.LoadMedia)
        }
    }

    if (homeState.isLoading) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            ShimmerHomeScreen()
        }
    }

    if (homeState.media != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            TrendingMediaListInHome(
                navController = navController,
                ctx = ctx,
                mediaList = homeState.media[0],
                title = "Movie - Trending",
                type = "movie"
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            TrendingMediaListInHome(
                navController = navController,
                ctx = ctx,
                mediaList = homeState.media[3],
                title = "Tv Series - Trending",
                type = "tv"
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendingMediaListInHome(
    navController: NavHostController,
    ctx: Context,
    mediaList: List<Media>,
    title: String,
    type: String
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        mediaList.size
    }

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
    }

    Spacer(modifier = Modifier.height(6.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(220.dp)
                .align(Alignment.Center),
            state = pagerState,
            pageSize = PageSize.Fill,
            pageSpacing = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.large)
                    .clickable {
                        navController.navigate(Screens.Detail.route + "/$type/${mediaList[it].id}")
                    }
            ) {
                mediaList[it].backdropPath?.let { backdropPath -> TrendingMediaBackdrop(ctx = ctx, imgUrl = backdropPath) }
            }
        }
    }
    
    Spacer(modifier = Modifier.height(12.dp))

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(mediaList.size) {
            if (it == pagerState.currentPage) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary)
                )
            } else {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(color = Color.Gray)
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
        }
    }

    LaunchedEffect(key1 = pagerState.currentPage, block = {
        launch {
            while (true) {
                delay(3000)
                withContext(NonCancellable) {
                    if (pagerState.canScrollForward) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        pagerState.animateScrollToPage(0)
                    }
                }
            }
        }
    })
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

    LazyRow {
        items(mediaList.size) {
            MediaCard(media = mediaList[it], onClick = {
                navController.navigate(Screens.Detail.route + "/$type/${mediaList[it].id}")
            })
        }
    }
}

@Composable
fun TrendingMediaBackdrop(ctx: Context, imgUrl: String) {
    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(ctx)
            .data(BuildConfig.IMG_BASE_URL + imgUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    val imageState = imagePainter.state

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        if (imageState is AsyncImagePainter.State.Success) {
            val imageBitmap = imageState.result.drawable.toBitmap()
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = "image media",
                contentScale = ContentScale.Crop
            )
        }

        if (imageState is AsyncImagePainter.State.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(75.dp)
                    .align(Alignment.Center)
                    .scale(0.5f)
            )
        }

        if (imageState is AsyncImagePainter.State.Error) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp),
                imageVector = Icons.Filled.BrokenImage,
                contentDescription = "image media error"
            )
        }
    }
}

@Composable
fun ShimmerHomeScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(24.dp)
                .shimmerEffect()
        )
    }

    Spacer(modifier = Modifier.height(6.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.large)
                .align(Alignment.Center)
                .shimmerEffect(),
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(24.dp)
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .height(20.dp)
                .shimmerEffect()
        )
    }

    Spacer(modifier = Modifier.height(6.dp))

    LazyRow(
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        items(5) {
            ShimmerCard()
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(24.dp)
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .height(20.dp)
                .shimmerEffect()
        )
    }

    Spacer(modifier = Modifier.height(6.dp))

    LazyRow(
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {
        items(5) {
            ShimmerCard()
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(24.dp)
                .shimmerEffect()
        )
    }

    Spacer(modifier = Modifier.height(6.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.large)
                .align(Alignment.Center)
                .shimmerEffect(),
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(24.dp)
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .height(20.dp)
                .shimmerEffect()
        )
    }

    Spacer(modifier = Modifier.height(6.dp))

    LazyRow {
        items(5) {
            ShimmerCard()
        }
    }
}