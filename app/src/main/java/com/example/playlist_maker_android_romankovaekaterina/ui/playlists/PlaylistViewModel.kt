package com.example.playlist_maker_android_romankovaekaterina.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_romankovaekaterina.data.database.DatabaseMock
import com.example.playlist_maker_android_romankovaekaterina.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_romankovaekaterina.domain.api.TracksRepository
import com.example.playlist_maker_android_romankovaekaterina.domain.models.Playlist
import com.example.playlist_maker_android_romankovaekaterina.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksRepository: TracksRepository,
    private val databaseRepository: DatabaseMock,
) : ViewModel() {

    val playlists: Flow<List<Playlist>> = flow {
        playlistsRepository.getAllPlaylists().collect { playlist ->
            emit(playlist)
        }
    }
    val favoriteList: Flow<List<Track>> = databaseRepository.getFavoriteTracks()

    fun createNewPlayList(namePlaylist: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(namePlaylist, description)
        }
    }

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        tracksRepository.insertTrackToPlaylist(track, playlistId)
    }

    suspend fun toggleFavorite(track: Track, isFavorite: Boolean) {
        tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
    }

    suspend fun deleteTrackFromPlaylist(track: Track) {
        tracksRepository.deleteTrackFromPlaylist(track)
    }

    suspend fun deletePlaylistById(id: Long) {
        tracksRepository.deleteTracksByPlaylistId(id)
        playlistsRepository.deletePlaylistById(id)
    }

    suspend fun isExist(track: Track): Track? {
        return tracksRepository.getTrackByNameAndArtist(track = track).firstOrNull()
    }
}