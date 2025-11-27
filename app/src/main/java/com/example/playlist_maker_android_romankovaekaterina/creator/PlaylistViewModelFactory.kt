package com.example.playlist_maker_android_romankovaekaterina.creator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android_romankovaekaterina.data.database.DatabaseMock
import com.example.playlist_maker_android_romankovaekaterina.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_romankovaekaterina.domain.api.TracksRepository
import com.example.playlist_maker_android_romankovaekaterina.ui.playlists.PlaylistViewModel

class PlaylistViewModelFactory(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository,
    private val database: DatabaseMock
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(playlistsRepository, tracksRepository, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}