# Liquid Glass Music

An Apple Music UI clone for Android featuring the **Liquid Glass** visual effect using the Kyant Backdrop library.

## Features

- **Liquid Glass UI Effects**: Beautiful glass morphism with blur, vibrancy, and lens refraction effects
- **Apple Music-inspired Design**: Faithful recreation of Apple Music's modern UI
- **5 Main Screens**: Listen Now, Browse, Radio, Library, Search
- **Full Now Playing Screen**: With animated controls and album art
- **Mini Player**: Persistent glass-effect mini player above navigation
- **Glass Bottom Navigation**: Frosted glass effect bottom navigation bar

## Requirements

- **Android 13+ (API 33)** - Required for lens effects in the Backdrop library
- **AndroidIDE** or Android Studio for building

## Building with AndroidIDE

1. Open AndroidIDE on your Android device
2. Clone or copy this project to your device
3. Open the project in AndroidIDE (select the `liq-glass` folder)
4. Wait for Gradle sync to complete
5. Build > Build APK

## Tech Stack

- **Kotlin 2.0.0** - Modern Kotlin features
- **Jetpack Compose** - Declarative UI
- **Material3** - Material Design 3 components
- **Navigation Compose** - Type-safe navigation
- **Backdrop Library** (io.github.kyant0:backdrop:1.0.4) - Liquid Glass effects
- **Coil** - Image loading

## Project Structure

```
app/src/main/java/com/liquidglass/music/
├── MainActivity.kt           # Entry point
├── MusicApp.kt              # Main app composable with navigation
├── LiquidGlassMusicApp.kt   # Application class
├── data/
│   ├── Models.kt            # Data classes (Song, Album, Playlist, etc.)
│   └── SampleData.kt        # Sample data for demo
├── navigation/
│   └── Navigation.kt        # Navigation routes and bottom nav items
└── ui/
    ├── components/
    │   ├── LiquidGlassComponents.kt    # Core glass effect components
    │   ├── LiquidGlassBottomNavBar.kt  # Glass navigation bar
    │   ├── LiquidGlassMiniPlayer.kt    # Glass mini player
    │   └── MusicCards.kt               # Album/Playlist/Song cards
    ├── screens/
    │   ├── ListenNowScreen.kt   # Home/discovery screen
    │   ├── BrowseScreen.kt      # Browse music screen
    │   ├── RadioScreen.kt       # Radio stations screen
    │   ├── LibraryScreen.kt     # User library screen
    │   ├── SearchScreen.kt      # Search screen
    │   └── NowPlayingScreen.kt  # Full player screen
    └── theme/
        ├── Color.kt         # Color definitions
        ├── Theme.kt         # App theme
        └── Typography.kt    # Typography styles
```

## Backdrop Library Usage

The app uses the Kyant Backdrop library for liquid glass effects:

```kotlin
// Create a backdrop
val backdrop = rememberLayerBackdrop {
    drawRect(backgroundColor)
    drawContent()
}

// Apply to content
Box(Modifier.layerBackdrop(backdrop)) {
    // Your content
}

// Draw glass effect
Box(
    Modifier.drawBackdrop(
        backdrop = backdrop,
        shape = { RoundedCornerShape(24.dp) },
        effects = {
            vibrancy()           // Increase saturation
            blur(4f.dp.toPx())   // Blur effect
            lens(16f.dp.toPx(), 32f.dp.toPx()) // Refraction effect
        },
        onDrawSurface = { drawRect(Color.White.copy(alpha = 0.5f)) }
    )
)
```

## Screenshots

The app replicates the Apple Music UI with:
- Gradient backgrounds derived from content
- Glass morphism effects on navigation and controls
- Smooth animations and press feedback
- Dark theme optimized design

## License

This project is for educational purposes demonstrating the Backdrop library.

## Credits

- [Kyant Backdrop Library](https://github.com/Kyant0/AndroidLiquidGlass) - Liquid Glass effects
- Apple Music - UI/UX inspiration
- [Picsum Photos](https://picsum.photos) - Placeholder images
