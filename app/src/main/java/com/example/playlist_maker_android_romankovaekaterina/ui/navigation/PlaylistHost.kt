package com.example.playlist_maker_android_romankovaekaterina.ui.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.playlist_maker_android_romankovaekaterina.ui.screen.FavoritesScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.screen.MainScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.screen.PlaylistsScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.screen.SettingsScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.screen.SearchRoute
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_romankovaekaterina.creator.Creator
import com.example.playlist_maker_android_romankovaekaterina.ui.screen.NewPlaylistScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.screen.TrackDetailsScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.playlists.PlaylistViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun PlaylistHost(navController: NavHostController) {

    val navigateUp: () -> Unit = { navController.navigateUp() }
    val toSearch: () -> Unit = { navController.navigate(AppScreen.Search.route) }
    val toSettings: () -> Unit = { navController.navigate(AppScreen.Settings.route) }
    val toFavorites: () -> Unit = { navController.navigate(route=AppScreen.Favorites.route)}
    val toPlaylists: () -> Unit = { navController.navigate(route = AppScreen.Playlists.route)}
    val toNewPlaylist: () -> Unit = { navController.navigate(route = AppScreen.NewPlaylist.route) }
    val playlistViewModel: PlaylistViewModel = viewModel(factory = Creator.providePlaylistViewModelFactory())

    NavHost(navController, startDestination = AppScreen.Main.route) {
        composable(AppScreen.Main.route) {
            MainScreen(
                onOpenSearch = toSearch,
                onOpenPlaylists = toPlaylists,
                onOpenFavorites = toFavorites,
                onOpenSettings = toSettings
            )
        }
        composable(AppScreen.Search.route) {
            SearchRoute(
                onBack = navigateUp,
                onTrackClick = { track ->
                    val encodedName = java.net.URLEncoder.encode(track.trackName, StandardCharsets.UTF_8.toString())
                    val encodedArtist = java.net.URLEncoder.encode(track.artistName, StandardCharsets.UTF_8.toString())
                    val encodedTime = java.net.URLEncoder.encode(track.trackTime, StandardCharsets.UTF_8.toString())
                    navController.navigate("${AppScreen.TrackDetails.route}/$encodedName/$encodedArtist/$encodedTime")
                }
            )
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(onBack = navigateUp)
        }
        composable(route=AppScreen.Playlists.route) {
            PlaylistsScreen(
                onBack = navigateUp,
                onAddNewPlaylist = toNewPlaylist,
                playlistViewModel = playlistViewModel
            )
        }
        composable(route = AppScreen.Favorites.route) {
            FavoritesScreen(onBack = navigateUp)
        }
        composable(route = AppScreen.NewPlaylist.route) {
            NewPlaylistScreen(
                onBack = navigateUp,
                playlistViewModel = playlistViewModel
            )
        }
        composable(
            route = "${AppScreen.TrackDetails.route}/{trackName}/{artistName}/{trackTime}",
            arguments = listOf(
                navArgument("trackName") { defaultValue = ""; type = androidx.navigation.NavType.StringType },
                navArgument("artistName") { defaultValue = ""; type = androidx.navigation.NavType.StringType },
                navArgument("trackTime") { defaultValue = ""; type = androidx.navigation.NavType.StringType }
            )
        ) { backStackEntry ->
            val trackNameArg = backStackEntry.arguments?.getString("trackName").orEmpty()
            val artistNameArg = backStackEntry.arguments?.getString("artistName").orEmpty()
            val trackTimeArg = backStackEntry.arguments?.getString("trackTime").orEmpty()

            val trackName = URLDecoder.decode(trackNameArg, StandardCharsets.UTF_8.toString())
            val artistName = URLDecoder.decode(artistNameArg, StandardCharsets.UTF_8.toString())
            val trackTime = URLDecoder.decode(trackTimeArg, StandardCharsets.UTF_8.toString())

            TrackDetailsScreen(
                trackName = trackName,
                artistName = artistName,
                trackTime = trackTime,
                onBack = navigateUp,
                playlistViewModel = playlistViewModel
            )
        }
    }
}
