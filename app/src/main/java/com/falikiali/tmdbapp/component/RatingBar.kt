package com.falikiali.tmdbapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double
) {
    val filledStars = kotlin.math.floor(rating).toInt()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(filledStars / 2) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Filled.Star,
                tint = Color.Yellow,
                contentDescription = "filled stars"
            )
        }

        if (filledStars % 2 == 1) {
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.AutoMirrored.Filled.StarHalf,
                tint = Color.Yellow,
                contentDescription = "half stars"
            )

            repeat(4 - filledStars / 2) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Outlined.StarOutline,
                    contentDescription = "outline stars"
                )
            }
        } else {
            repeat(5 - filledStars / 2) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Outlined.StarOutline,
                    contentDescription = "outline stars"
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = "$rating",
            fontSize = 16.sp
        )
    }
}