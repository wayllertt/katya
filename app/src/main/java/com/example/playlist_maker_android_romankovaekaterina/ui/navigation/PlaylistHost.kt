package com.example.playlist_maker_android_romankovaekaterina.ui.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.playlist_maker_android_romankovaekaterina.ui.activity.FavoritesScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.activity.MainScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.activity.PlaylistsScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.activity.SearchScreen
import com.example.playlist_maker_android_romankovaekaterina.ui.activity.SettingsScreen

@Composable
fun PlaylistHost(navController: NavHostController) {

    val navigateUp: () -> Unit = { navController.navigateUp() }
    val toSearch: () -> Unit = { navController.navigate(AppScreen.Search.route) }
    val toSettings: () -> Unit = { navController.navigate(AppScreen.Settings.route) }
    val toFavorites: () -> Unit = { navController.navigate(route=AppScreen.Favorites.route)}
    val toPlaylists: () -> Unit = { navController.navigate(route = AppScreen.Playlists.route)}

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
            SearchScreen(onBack = navigateUp)
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(onBack = navigateUp)
        }
        composable(route=AppScreen.Playlists.route) {
            PlaylistsScreen(onBack = navigateUp)
        }
        composable(route = AppScreen.Favorites.route) {
            FavoritesScreen(onBack = navigateUp)
        }
    }
}
