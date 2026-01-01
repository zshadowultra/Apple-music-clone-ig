package com.liquidglass.music.data

import androidx.compose.ui.graphics.Color
import com.liquidglass.music.ui.theme.AccentBlue
import com.liquidglass.music.ui.theme.AccentGreen
import com.liquidglass.music.ui.theme.AccentOrange
import com.liquidglass.music.ui.theme.AccentPurple
import com.liquidglass.music.ui.theme.AccentTeal
import com.liquidglass.music.ui.theme.MusicPink

object SampleData {
    // Placeholder image URLs from picsum.photos for demo
    private const val ALBUM_ART_BASE = "https://picsum.photos/seed"

    val sampleSongs = listOf(
        Song(
            id = "1",
            title = "Midnight Dreams",
            artist = "Luna Nova",
            album = "Starlight Sessions",
            albumArtUrl = "$ALBUM_ART_BASE/album1/400/400",
            duration = 234000,
            dominantColor = AccentPurple
        ),
        Song(
            id = "2",
            title = "Electric Sunrise",
            artist = "Neon Pulse",
            album = "Digital Dawn",
            albumArtUrl = "$ALBUM_ART_BASE/album2/400/400",
            duration = 198000,
            dominantColor = AccentOrange
        ),
        Song(
            id = "3",
            title = "Ocean Waves",
            artist = "Coastal Vibes",
            album = "Summer Escape",
            albumArtUrl = "$ALBUM_ART_BASE/album3/400/400",
            duration = 267000,
            dominantColor = AccentTeal
        ),
        Song(
            id = "4",
            title = "City Lights",
            artist = "Urban Soul",
            album = "Metropolitan",
            albumArtUrl = "$ALBUM_ART_BASE/album4/400/400",
            duration = 212000,
            dominantColor = MusicPink
        ),
        Song(
            id = "5",
            title = "Forest Rain",
            artist = "Nature Sounds",
            album = "Peaceful Moments",
            albumArtUrl = "$ALBUM_ART_BASE/album5/400/400",
            duration = 289000,
            dominantColor = AccentGreen
        ),
        Song(
            id = "6",
            title = "Cosmic Journey",
            artist = "Space Explorer",
            album = "Galaxies Away",
            albumArtUrl = "$ALBUM_ART_BASE/album6/400/400",
            duration = 345000,
            dominantColor = AccentBlue
        )
    )

    val sampleAlbums = listOf(
        Album(
            id = "a1",
            title = "Starlight Sessions",
            artist = "Luna Nova",
            artworkUrl = "$ALBUM_ART_BASE/album1/400/400",
            year = 2024
        ),
        Album(
            id = "a2",
            title = "Digital Dawn",
            artist = "Neon Pulse",
            artworkUrl = "$ALBUM_ART_BASE/album2/400/400",
            year = 2024
        ),
        Album(
            id = "a3",
            title = "Summer Escape",
            artist = "Coastal Vibes",
            artworkUrl = "$ALBUM_ART_BASE/album3/400/400",
            year = 2023
        ),
        Album(
            id = "a4",
            title = "Metropolitan",
            artist = "Urban Soul",
            artworkUrl = "$ALBUM_ART_BASE/album4/400/400",
            year = 2024
        ),
        Album(
            id = "a5",
            title = "Peaceful Moments",
            artist = "Nature Sounds",
            artworkUrl = "$ALBUM_ART_BASE/album5/400/400",
            year = 2023
        ),
        Album(
            id = "a6",
            title = "Galaxies Away",
            artist = "Space Explorer",
            artworkUrl = "$ALBUM_ART_BASE/album6/400/400",
            year = 2024
        )
    )

    val samplePlaylists = listOf(
        Playlist(
            id = "p1",
            title = "Today's Hits",
            description = "The biggest songs right now",
            artworkUrl = "$ALBUM_ART_BASE/playlist1/400/400",
            curatorName = "Apple Music"
        ),
        Playlist(
            id = "p2",
            title = "Chill Vibes",
            description = "Relax and unwind",
            artworkUrl = "$ALBUM_ART_BASE/playlist2/400/400",
            curatorName = "Apple Music"
        ),
        Playlist(
            id = "p3",
            title = "Workout Energy",
            description = "Power through your workout",
            artworkUrl = "$ALBUM_ART_BASE/playlist3/400/400",
            curatorName = "Apple Music"
        ),
        Playlist(
            id = "p4",
            title = "Late Night Jazz",
            description = "Smooth jazz for the evening",
            artworkUrl = "$ALBUM_ART_BASE/playlist4/400/400",
            curatorName = "Apple Music"
        )
    )

    val sampleRadioStations = listOf(
        RadioStation(
            id = "r1",
            name = "Apple Music 1",
            description = "The new music that matters",
            artworkUrl = "$ALBUM_ART_BASE/radio1/400/400",
            isLive = true
        ),
        RadioStation(
            id = "r2",
            name = "Apple Music Hits",
            description = "Songs you know and love",
            artworkUrl = "$ALBUM_ART_BASE/radio2/400/400",
            isLive = true
        ),
        RadioStation(
            id = "r3",
            name = "Apple Music Country",
            description = "Where country music lives",
            artworkUrl = "$ALBUM_ART_BASE/radio3/400/400",
            isLive = true
        )
    )

    val currentSong = sampleSongs.first()
}
