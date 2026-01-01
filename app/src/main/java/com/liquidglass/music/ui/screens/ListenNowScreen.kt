package com.liquidglass.music.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.liquidglass.music.data.SampleData
import com.liquidglass.music.ui.components.AlbumCard
import com.liquidglass.music.ui.components.FeaturedAlbumCard
import com.liquidglass.music.ui.components.PlaylistCard
import com.liquidglass.music.ui.components.SongRow
import com.liquidglass.music.ui.theme.MusicPink

/**
 * Listen Now screen - Apple Music's home/discovery screen
 */
@Composable
fun ListenNowScreen(
    onAlbumClick: (String) -> Unit,
    onPlaylistClick: (String) -> Unit,
    onSongClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MusicPink.copy(alpha = 0.3f),
                        Color.Black,
                        Color.Black
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 200.dp) // Space for mini player + nav
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .padding(top = 48.dp) // Status bar padding
                ) {
                    Text(
                        text = "Listen Now",
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White
                    )
                }
            }

            // Featured Section
            item {
                SectionHeader(title = "Top Picks For You")
            }

            item {
                if (SampleData.sampleAlbums.isNotEmpty()) {
                    FeaturedAlbumCard(
                        album = SampleData.sampleAlbums.first(),
                        onClick = { onAlbumClick(SampleData.sampleAlbums.first().id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }
            }

            // Recently Played
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(title = "Recently Played")
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(SampleData.sampleAlbums) { album ->
                        AlbumCard(
                            album = album,
                            onClick = { onAlbumClick(album.id) }
                        )
                    }
                }
            }

            // Made For You
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(title = "Made For You")
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(SampleData.samplePlaylists) { playlist ->
                        PlaylistCard(
                            playlist = playlist,
                            onClick = { onPlaylistClick(playlist.id) }
                        )
                    }
                }
            }

            // New Music
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(title = "New Music")
            }

            items(SampleData.sampleSongs.take(5)) { song ->
                SongRow(
                    song = song,
                    onClick = { onSongClick(song.id) }
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    )
}
