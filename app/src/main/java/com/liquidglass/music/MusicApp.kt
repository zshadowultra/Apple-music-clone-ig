package com.liquidglass.music

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kyant.backdrop.layerBackdrop
import com.kyant.backdrop.rememberLayerBackdrop
import com.liquidglass.music.data.PlaybackState
import com.liquidglass.music.data.RepeatMode
import com.liquidglass.music.data.SampleData
import com.liquidglass.music.navigation.Screen
import com.liquidglass.music.ui.components.LiquidGlassBottomNavBar
import com.liquidglass.music.ui.components.LiquidGlassMiniPlayer
import com.liquidglass.music.ui.screens.BrowseScreen
import com.liquidglass.music.ui.screens.LibraryScreen
import com.liquidglass.music.ui.screens.ListenNowScreen
import com.liquidglass.music.ui.screens.NowPlayingScreen
import com.liquidglass.music.ui.screens.RadioScreen
import com.liquidglass.music.ui.screens.SearchScreen

@Composable
fun MusicApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: Screen.ListenNow.route

    // Playback state
    var playbackState by remember {
        mutableStateOf(
            PlaybackState(
                currentSong = SampleData.currentSong,
                isPlaying = false,
                progress = 0.3f,
                shuffleEnabled = false,
                repeatMode = RepeatMode.OFF
            )
        )
    }

    // Show bottom bar and mini player only on main screens
    val showBottomBar = currentRoute in listOf(
        Screen.ListenNow.route,
        Screen.Browse.route,
        Screen.Radio.route,
        Screen.Library.route,
        Screen.Search.route
    )

    // Create backdrop for the whole app
    val backdrop = rememberLayerBackdrop {
        drawRect(Color.Black)
        drawContent()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .layerBackdrop(backdrop)
    ) {
        // Main Navigation Host
        NavHost(
            navController = navController,
            startDestination = Screen.ListenNow.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Screen.ListenNow.route) {
                ListenNowScreen(
                    onAlbumClick = { /* Navigate to album detail */ },
                    onPlaylistClick = { /* Navigate to playlist detail */ },
                    onSongClick = { songId ->
                        val song = SampleData.sampleSongs.find { it.id == songId }
                        song?.let {
                            playbackState = playbackState.copy(currentSong = it, isPlaying = true)
                        }
                    }
                )
            }

            composable(Screen.Browse.route) {
                BrowseScreen(
                    onAlbumClick = { /* Navigate to album detail */ },
                    onPlaylistClick = { /* Navigate to playlist detail */ }
                )
            }

            composable(Screen.Radio.route) {
                RadioScreen(
                    onStationClick = { /* Play radio station */ }
                )
            }

            composable(Screen.Library.route) {
                LibraryScreen(
                    onAlbumClick = { /* Navigate to album detail */ },
                    onSongClick = { songId ->
                        val song = SampleData.sampleSongs.find { it.id == songId }
                        song?.let {
                            playbackState = playbackState.copy(currentSong = it, isPlaying = true)
                        }
                    }
                )
            }

            composable(Screen.Search.route) {
                SearchScreen(
                    onAlbumClick = { /* Navigate to album detail */ },
                    onSongClick = { songId ->
                        val song = SampleData.sampleSongs.find { it.id == songId }
                        song?.let {
                            playbackState = playbackState.copy(currentSong = it, isPlaying = true)
                        }
                    }
                )
            }

            composable(Screen.NowPlaying.route) {
                NowPlayingScreen(
                    song = playbackState.currentSong ?: SampleData.currentSong,
                    isPlaying = playbackState.isPlaying,
                    progress = playbackState.progress,
                    shuffleEnabled = playbackState.shuffleEnabled,
                    repeatMode = playbackState.repeatMode,
                    onBackClick = { navController.popBackStack() },
                    onPlayPauseClick = {
                        playbackState = playbackState.copy(isPlaying = !playbackState.isPlaying)
                    },
                    onNextClick = {
                        val currentIndex = SampleData.sampleSongs.indexOfFirst {
                            it.id == playbackState.currentSong?.id
                        }
                        val nextIndex = (currentIndex + 1) % SampleData.sampleSongs.size
                        playbackState = playbackState.copy(
                            currentSong = SampleData.sampleSongs[nextIndex],
                            progress = 0f
                        )
                    },
                    onPreviousClick = {
                        val currentIndex = SampleData.sampleSongs.indexOfFirst {
                            it.id == playbackState.currentSong?.id
                        }
                        val prevIndex = if (currentIndex > 0) currentIndex - 1 else SampleData.sampleSongs.size - 1
                        playbackState = playbackState.copy(
                            currentSong = SampleData.sampleSongs[prevIndex],
                            progress = 0f
                        )
                    },
                    onShuffleClick = {
                        playbackState = playbackState.copy(shuffleEnabled = !playbackState.shuffleEnabled)
                    },
                    onRepeatClick = {
                        playbackState = playbackState.copy(
                            repeatMode = when (playbackState.repeatMode) {
                                RepeatMode.OFF -> RepeatMode.ALL
                                RepeatMode.ALL -> RepeatMode.ONE
                                RepeatMode.ONE -> RepeatMode.OFF
                            }
                        )
                    },
                    onProgressChange = { newProgress ->
                        playbackState = playbackState.copy(progress = newProgress)
                    }
                )
            }
        }

        // Bottom bar with mini player and navigation
        AnimatedVisibility(
            visible = showBottomBar,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeContentPadding()
            ) {
                // Mini Player
                LiquidGlassMiniPlayer(
                    song = playbackState.currentSong,
                    isPlaying = playbackState.isPlaying,
                    onPlayPauseClick = {
                        playbackState = playbackState.copy(isPlaying = !playbackState.isPlaying)
                    },
                    onNextClick = {
                        val currentIndex = SampleData.sampleSongs.indexOfFirst {
                            it.id == playbackState.currentSong?.id
                        }
                        val nextIndex = (currentIndex + 1) % SampleData.sampleSongs.size
                        playbackState = playbackState.copy(
                            currentSong = SampleData.sampleSongs[nextIndex],
                            progress = 0f
                        )
                    },
                    onPlayerClick = {
                        navController.navigate(Screen.NowPlaying.route)
                    },
                    backdrop = backdrop,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                // Bottom Navigation Bar
                LiquidGlassBottomNavBar(
                    selectedRoute = currentRoute,
                    onItemSelected = { route ->
                        if (route != currentRoute) {
                            navController.navigate(route) {
                                popUpTo(Screen.ListenNow.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    backdrop = backdrop,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}
