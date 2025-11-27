package com.example.playlist_maker_android_romankovaekaterina.ui.navigation

enum class AppScreen(val route: String) {
    Main("main"),
    Search("search"),
    Settings("settings"),

    Favorites( "favorites"),

    Playlists(route = "playlists"),

    NewPlaylist("new_playlist"),

    TrackDetails("track_details")
}