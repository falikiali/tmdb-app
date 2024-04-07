package com.falikiali.tmdbapp.presentation.detail

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.falikiali.tmdbapp.component.RatingBar
import com.falikiali.tmdbapp.component.ShimmerCard
import com.falikiali.tmdbapp.component.shimmerEffect
import com.falikiali.tmdbapp.domain.model.Media
import com.falikiali.tmdbapp.domain.model.MediaDetail
import com.falikiali.tmdbapp.helper.Screens
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    mediaType: String,
    mediaId: Int,
    navController: NavHostController,
    detailState: DetailState,
    onEvent: (DetailUiEvent) -> Unit
) {
    val ctx = LocalContext.current

    LaunchedEffect(key1 = true) {
        onEvent(DetailUiEvent.LoadDetailMedia(mediaType = mediaType, mediaId = mediaId))
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(DetailUiEvent.LoadStatusBookmarkMedia(mediaId = mediaId))
    }

    if (detailState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                ShimmerDetailScreen(navController = navController)
            }
        }
    }

    if (detailState.detailMedia != null) {
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    DetailBackdropImage(ctx = ctx, imgUrl = detailState.detailMedia.backdropPath)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        DetailPosterImage(ctx = ctx, imgUrl = detailState.detailMedia.posterPath)

                        Spacer(modifier = Modifier.width(18.dp))

                        DetailInfo(mediaType = mediaType, media = detailState.detailMedia)
                    }

                    FilledTonalIconButton(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 48.dp)
                            .align(Alignment.TopStart)
                            .clip(CircleShape),
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "back button")
                    }

                    if (detailState.isBookmark != null) {
                        FilledTonalIconButton(
                            modifier = Modifier
                                .padding(end = 8.dp, top = 48.dp)
                                .align(Alignment.TopEnd)
                                .clip(CircleShape),
                            onClick = {
                                val media = Media(
                                    title = detailState.detailMedia.title,
                                    name = detailState.detailMedia.name,
                                    posterPath = detailState.detailMedia.posterPath,
                                    releaseDate = detailState.detailMedia.releaseDate,
                                    firstAirDate = detailState.detailMedia.firstAirDate,
                                    id = mediaId,
                                    mediaType = mediaType,
                                    voteAverage = detailState.detailMedia.voteAverage
                                )
                                onEvent(DetailUiEvent.OnAddOrRemoveBookmarkMedia(detailState.isBookmark, media))
                            }
                        ) {
                            if (!detailState.isBookmark) {
                                Icon(imageVector = Icons.Outlined.BookmarkBorder, contentDescription = "back button")
                            } else {
                                Icon(imageVector = Icons.Filled.Bookmark, contentDescription = "back button")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (detailState.detailMedia.tagline != null && detailState.detailMedia.tagline != "") {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = "\"${detailState.detailMedia.tagline}\"",
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                if (!detailState.detailMedia.overview.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = "Overview",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = detailState.detailMedia.overview,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!detailState.detailMedia.similarMedia.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Similar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp
                        )

                        Text(
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Screens.Similar.route + "/$mediaType/$mediaId")
                                },
                            text = "See All",
                            fontWeight = FontWeight.Thin,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                    ) {
                        items(detailState.detailMedia.similarMedia.size) {
                            MediaCard(media = detailState.detailMedia.similarMedia[it], onClick = {
                                navController.navigate(Screens.Detail.route + "/${mediaType}/${detailState.detailMedia.similarMedia[it].id}")
                            })
                        }
                    }
                }
            }
        }
    }

    if (detailState.isError != null) {
        Box(
            modifier = modifier.fillMaxSize(),
        ) {
            IconButton(
                modifier = Modifier
                    .padding(start = 8.dp, top = 48.dp)
                    .align(Alignment.TopStart),
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "back button")
            }

            ErrorScreen(
                modifier = Modifier
                    .align(Alignment.Center),
                messageError = detailState.isError
            ) {
                onEvent(DetailUiEvent.Refresh(mediaType = mediaType, mediaId = mediaId))
            }
        }
    }
}

@Composable
fun DetailBackdropImage(ctx: Context, imgUrl: String? = null) {
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
fun DetailPosterImage(ctx: Context, imgUrl: String? = null) {
    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(ctx)
            .data(BuildConfig.IMG_BASE_URL + imgUrl)
            .size(Size.ORIGINAL)
            .build()
    )
    val imageState = imagePainter.state
    
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(215.dp))
        
        Card(
            modifier = Modifier
                .width(160.dp)
                .height(250.dp)
                .clip(MaterialTheme.shapes.large)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
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
    }
}

@Composable
fun DetailInfo(mediaType: String, media: MediaDetail) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val date = if (mediaType == "tv") {
        media.firstAirDate?.let { inputFormat.parse(it) }
    } else {
        media.releaseDate?.let { inputFormat.parse(it) }
    }
    val newFormatDate = date?.let { outputFormat.format(it) }

    Column(
        modifier = Modifier
            .padding(end = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(296.dp))

        Text(
            modifier = Modifier,
            text = (if (mediaType == "tv") media.name else media.title) ?: "Unknown",
            fontSize = 22.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 6.dp, vertical = 0.5.dp),
            text = if (media.adult == false) "-12" else "+18",
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = FontFamily.SansSerif,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        RatingBar(
            modifier = Modifier,
            rating = media.voteAverage ?: 0.0
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (newFormatDate != null) {
            Text(
                text = newFormatDate,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@Composable
fun ShimmerDetailScreen(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .shimmerEffect()
        )

        IconButton(
            modifier = Modifier
                .padding(start = 8.dp, top = 48.dp)
                .align(Alignment.TopStart),
            onClick = {
                navController.popBackStack()
            }
        ) {
            Icon(imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "back button")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(215.dp))

                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(250.dp)
                        .clip(MaterialTheme.shapes.large)
                        .shimmerEffect()
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            Column(
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(296.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(20.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(24.dp)
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(24.dp)
                        .shimmerEffect()
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp)
            .shimmerEffect()
    )

    Spacer(modifier = Modifier.height(20.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .height(24.dp)
            .padding(horizontal = 16.dp)
            .shimmerEffect()
    )

    Spacer(modifier = Modifier.height(6.dp))

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 16.dp)
            .shimmerEffect()
    )

    Spacer(modifier = Modifier.height(20.dp))

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
}