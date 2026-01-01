package com.liquidglass.music.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.liquidglass.music.data.SampleData
import com.liquidglass.music.ui.components.AlbumCard
import com.liquidglass.music.ui.components.SongRow
import com.liquidglass.music.ui.theme.AccentGreen
import com.liquidglass.music.ui.theme.MusicPink

/**
 * Library screen - User's personal music library
 */
@Composable
fun LibraryScreen(
    onAlbumClick: (String) -> Unit,
    onSongClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        AccentGreen.copy(alpha = 0.3f),
                        Color.Black,
                        Color.Black
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 200.dp)
        ) {
            // Header
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .padding(top = 48.dp)
                ) {
                    Text(
                        text = "Library",
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White
                    )
                }
            }

            // Library Categories
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    LibraryCategoryRow(
                        icon = Icons.Default.PlaylistPlay,
                        title = "Playlists",
                        onClick = { }
                    )
                    LibraryCategoryRow(
                        icon = Icons.Default.Person,
                        title = "Artists",
                        onClick = { }
                    )
                    LibraryCategoryRow(
                        icon = Icons.Default.Album,
                        title = "Albums",
                        onClick = { }
                    )
                    LibraryCategoryRow(
                        icon = Icons.Default.MusicNote,
                        title = "Songs",
                        onClick = { }
                    )
                    LibraryCategoryRow(
                        icon = Icons.Default.Download,
                        title = "Downloaded",
                        onClick = { }
                    )
                }
            }

            // Recently Added
            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(title = "Recently Added")
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

            // Favorites
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MusicPink,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Favorite Songs",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
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
private fun LibraryCategoryRow(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MusicPink),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.size(24.dp)
        )
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
