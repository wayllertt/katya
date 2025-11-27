package com.example.playlist_maker_android_romankovaekaterina.data.repository

import com.example.playlist_maker_android_romankovaekaterina.data.database.DatabaseMock
import com.example.playlist_maker_android_romankovaekaterina.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_romankovaekaterina.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
    private val database: DatabaseMock
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return database.getPlaylist(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return database.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        database.addNewPlaylist(
            name = name,
            description = description
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.deletePlaylistById(playlistId = id)
    }
}