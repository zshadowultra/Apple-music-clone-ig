package com.liquidglass.music.ui.screens

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
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QueueMusic
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.layerBackdrop
import com.kyant.backdrop.rememberLayerBackdrop
import com.liquidglass.music.data.RepeatMode
import com.liquidglass.music.data.Song
import com.liquidglass.music.ui.theme.GlassWhite
import com.liquidglass.music.ui.theme.MusicPink
import kotlinx.coroutines.launch

/**
 * Full-screen Now Playing screen with liquid glass effects
 */
@Composable
fun NowPlayingScreen(
    song: Song,
    isPlaying: Boolean,
    progress: Float,
    shuffleEnabled: Boolean,
    repeatMode: RepeatMode,
    onBackClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit,
    onProgressChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val backdrop = rememberLayerBackdrop {
        drawRect(Color.Black)
        drawContent()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .layerBackdrop(backdrop)
    ) {
        // Background with blurred album art
        NowPlayingBackground(song = song)

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            TopBar(
                onBackClick = onBackClick,
                backdrop = backdrop
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Album Art with glass effect
            GlassAlbumArt(
                song = song,
                backdrop = backdrop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Song Info
            SongInfo(song = song)

            Spacer(modifier = Modifier.height(24.dp))

            // Progress Bar
            ProgressSection(
                progress = progress,
                duration = song.duration,
                onProgressChange = onProgressChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Playback Controls
            PlaybackControls(
                isPlaying = isPlaying,
                shuffleEnabled = shuffleEnabled,
                repeatMode = repeatMode,
                onPlayPauseClick = onPlayPauseClick,
                onNextClick = onNextClick,
                onPreviousClick = onPreviousClick,
                onShuffleClick = onShuffleClick,
                onRepeatClick = onRepeatClick,
                backdrop = backdrop
            )

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Actions
            BottomActions(backdrop = backdrop)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun NowPlayingBackground(song: Song) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Blurred album art as background
        AsyncImage(
            model = song.albumArtUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(100.dp),
            contentScale = ContentScale.Crop
        )

        // Dark overlay gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )
    }
}

@Composable
private fun TopBar(
    onBackClick: () -> Unit,
    backdrop: Backdrop
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlassIconButton(
            icon = Icons.Default.ArrowBackIosNew,
            contentDescription = "Back",
            onClick = onBackClick,
            backdrop = backdrop
        )

        Text(
            text = "Now Playing",
            style = MaterialTheme.typography.titleSmall,
            color = Color.White.copy(alpha = 0.8f)
        )

        GlassIconButton(
            icon = Icons.Default.MoreHoriz,
            contentDescription = "More options",
            onClick = { },
            backdrop = backdrop
        )
    }
}

@Composable
private fun GlassAlbumArt(
    song: Song,
    backdrop: Backdrop,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { RoundedCornerShape(24.dp) },
                effects = {
                    vibrancy()
                    blur(8f.dp.toPx())
                    lens(24f.dp.toPx(), 48f.dp.toPx(), true)
                },
                onDrawSurface = { drawRect(Color.White.copy(alpha = 0.2f)) }
            )
            .padding(8.dp)
    ) {
        AsyncImage(
            model = song.albumArtUrl,
            contentDescription = song.album,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun SongInfo(song: Song) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = song.title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = song.artist,
            style = MaterialTheme.typography.bodyLarge,
            color = MusicPink,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ProgressSection(
    progress: Float,
    duration: Long,
    onProgressChange: (Float) -> Unit
) {
    var sliderProgress by remember { mutableFloatStateOf(progress) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            value = sliderProgress,
            onValueChange = { sliderProgress = it },
            onValueChangeFinished = { onProgressChange(sliderProgress) },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatDuration((duration * sliderProgress).toLong()),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f)
            )
            Text(
                text = formatDuration(duration),
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun PlaybackControls(
    isPlaying: Boolean,
    shuffleEnabled: Boolean,
    repeatMode: RepeatMode,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onRepeatClick: () -> Unit,
    backdrop: Backdrop
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Shuffle
        IconButton(onClick = onShuffleClick) {
            Icon(
                imageVector = Icons.Default.Shuffle,
                contentDescription = "Shuffle",
                tint = if (shuffleEnabled) MusicPink else Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
        }

        // Previous
        GlassIconButton(
            icon = Icons.Default.SkipPrevious,
            contentDescription = "Previous",
            onClick = onPreviousClick,
            backdrop = backdrop,
            size = 56
        )

        // Play/Pause
        GlassPlayPauseButton(
            isPlaying = isPlaying,
            onClick = onPlayPauseClick,
            backdrop = backdrop
        )

        // Next
        GlassIconButton(
            icon = Icons.Default.SkipNext,
            contentDescription = "Next",
            onClick = onNextClick,
            backdrop = backdrop,
            size = 56
        )

        // Repeat
        IconButton(onClick = onRepeatClick) {
            Icon(
                imageVector = when (repeatMode) {
                    RepeatMode.ONE -> Icons.Default.RepeatOne
                    else -> Icons.Default.Repeat
                },
                contentDescription = "Repeat",
                tint = if (repeatMode != RepeatMode.OFF) MusicPink else Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun GlassPlayPauseButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
    backdrop: Backdrop
) {
    val animationScope = rememberCoroutineScope()
    val pressAnimation = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .size(72.dp)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { CircleShape },
                effects = {
                    vibrancy()
                    blur(6f.dp.toPx())
                    lens(20f.dp.toPx(), 40f.dp.toPx())
                },
                layerBlock = {
                    val scale = lerp(1f, 0.92f, pressAnimation.value)
                    scaleX = scale
                    scaleY = scale
                },
                onDrawSurface = { drawRect(Color.White.copy(alpha = 0.5f)) }
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
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = Color.Black,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
private fun GlassIconButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    backdrop: Backdrop,
    size: Int = 44
) {
    val animationScope = rememberCoroutineScope()
    val pressAnimation = remember { Animatable(0f) }

    Box(
        modifier = Modifier
            .size(size.dp)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { CircleShape },
                effects = {
                    vibrancy()
                    blur(4f.dp.toPx())
                    lens(12f.dp.toPx(), 24f.dp.toPx())
                },
                layerBlock = {
                    val scale = lerp(1f, 0.9f, pressAnimation.value)
                    scaleX = scale
                    scaleY = scale
                },
                onDrawSurface = { drawRect(Color.White.copy(alpha = 0.3f)) }
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
            modifier = Modifier.size((size * 0.5f).dp)
        )
    }
}

@Composable
private fun BottomActions(backdrop: Backdrop) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        GlassIconButton(
            icon = Icons.Default.Replay10,
            contentDescription = "Replay 10 seconds",
            onClick = { },
            backdrop = backdrop,
            size = 48
        )

        GlassIconButton(
            icon = Icons.Default.QueueMusic,
            contentDescription = "Queue",
            onClick = { },
            backdrop = backdrop,
            size = 48
        )

        GlassIconButton(
            icon = Icons.Default.Forward10,
            contentDescription = "Forward 10 seconds",
            onClick = { },
            backdrop = backdrop,
            size = 48
        )
    }
}

private fun formatDuration(durationMs: Long): String {
    val totalSeconds = durationMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}
