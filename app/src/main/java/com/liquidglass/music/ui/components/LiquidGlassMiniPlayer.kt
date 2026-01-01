package com.liquidglass.music.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.drawBackdrop
import com.liquidglass.music.data.Song
import kotlinx.coroutines.launch

/**
 * Mini player bar that appears above the bottom navigation
 * Features liquid glass effect with album art preview
 */
@Composable
fun LiquidGlassMiniPlayer(
    song: Song?,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlayerClick: () -> Unit,
    modifier: Modifier = Modifier,
    backdrop: Backdrop
) {
    if (song == null) return

    val animationScope = rememberCoroutineScope()
    val pressAnimation = remember { Animatable(0f) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { RoundedCornerShape(16.dp) },
                effects = {
                    vibrancy()
                    blur(6f.dp.toPx())
                    lens(12f.dp.toPx(), 24f.dp.toPx())
                },
                layerBlock = {
                    val scale = lerp(1f, 0.98f, pressAnimation.value)
                    scaleX = scale
                    scaleY = scale
                },
                onDrawSurface = {
                    // Draw a gradient overlay with the song's dominant color
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                song.dominantColor.copy(alpha = 0.4f),
                                Color.White.copy(alpha = 0.3f)
                            )
                        )
                    )
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onPlayerClick
            )
            .pointerInput(animationScope) {
                val animationSpec = spring<Float>(0.6f, 400f, 0.001f)
                awaitEachGesture {
                    awaitFirstDown()
                    animationScope.launch {
                        pressAnimation.animateTo(1f, animationSpec)
                    }
                    waitForUpOrCancellation()
                    animationScope.launch {
                        pressAnimation.animateTo(0f, animationSpec)
                    }
                }
            }
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Album Art
            AsyncImage(
                model = song.albumArtUrl,
                contentDescription = song.album,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // Song Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = song.title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Play/Pause Button
            MiniPlayerControlButton(
                icon = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                onClick = onPlayPauseClick,
                backdrop = backdrop
            )

            // Next Button
            MiniPlayerControlButton(
                icon = Icons.Default.SkipNext,
                contentDescription = "Next",
                onClick = onNextClick,
                backdrop = backdrop
            )
        }
    }
}

@Composable
private fun MiniPlayerControlButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    backdrop: Backdrop
) {
    val animationScope = rememberCoroutineScope()
    val pressAnimation = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .size(36.dp)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { CircleShape },
                effects = {
                    vibrancy()
                    blur(2f.dp.toPx())
                    lens(8f.dp.toPx(), 16f.dp.toPx())
                },
                layerBlock = {
                    val scale = lerp(1f, 0.9f, pressAnimation.value)
                    scaleX = scale
                    scaleY = scale
                },
                onDrawSurface = { drawRect(Color.White.copy(alpha = 0.25f)) }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .pointerInput(animationScope) {
                val animationSpec = spring<Float>(0.5f, 300f, 0.001f)
                awaitEachGesture {
                    awaitFirstDown()
                    animationScope.launch {
                        pressAnimation.animateTo(1f, animationSpec)
                    }
                    waitForUpOrCancellation()
                    animationScope.launch {
                        pressAnimation.animateTo(0f, animationSpec)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}
