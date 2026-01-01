package com.liquidglass.music.data

import androidx.compose.ui.graphics.Color

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val albumArtUrl: String,
    val duration: Long = 0L, // Duration in milliseconds
    val dominantColor: Color = Color(0xFF1C1C1E)
)

data class Album(
    val id: String,
    val title: String,
    val artist: String,
    val artworkUrl: String,
    val songs: List<Song> = emptyList(),
    val year: Int = 2024
)

data class Playlist(
    val id: String,
    val title: String,
    val description: String,
    val artworkUrl: String,
    val songs: List<Song> = emptyList(),
    val curatorName: String = "Apple Music"
)

data class Artist(
    val id: String,
    val name: String,
    val imageUrl: String,
    val genre: String = ""
)

data class RadioStation(
    val id: String,
    val name: String,
    val description: String,
    val artworkUrl: String,
    val isLive: Boolean = false
)

// Playback state
data class PlaybackState(
    val currentSong: Song? = null,
    val isPlaying: Boolean = false,
    val progress: Float = 0f,
    val currentPosition: Long = 0L,
    val shuffleEnabled: Boolean = false,
    val repeatMode: RepeatMode = RepeatMode.OFF
)

enum class RepeatMode {
    OFF, ALL, ONE
}
