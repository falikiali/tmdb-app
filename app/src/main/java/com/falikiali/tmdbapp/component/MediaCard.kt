package com.falikiali.tmdbapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.falikiali.tmdbapp.BuildConfig
import com.falikiali.tmdbapp.domain.model.Media

@Composable
fun MediaCard(
    modifier: Modifier = Modifier,
    media: Media,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current

    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(BuildConfig.IMG_BASE_URL + media.posterPath)
            .size(Size.ORIGINAL)
            .build()
    )
    val imageState = imagePainter.state

    Card(
        modifier = modifier
            .width(190.dp)
            .padding(6.dp)
            .clickable {
                onClick?.invoke()
            },
        shape = MaterialTheme.shapes.large
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(6.dp)
                    .clip(MaterialTheme.shapes.large)
            ) {
                if (imageState is AsyncImagePainter.State.Success) {
                    val imageBitmap = imageState.result.drawable.toBitmap()
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = "image media",
                        contentScale = ContentScale.Crop
                    )
                }

                if (imageState is AsyncImagePainter.State.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(150.dp)
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

            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                text = (media.name ?: media.title) ?: "Unknown",
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            media.voteAverage?.let {
                RatingBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
                    rating = it
                )
            }
        }
    }
}