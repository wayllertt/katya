package com.example.playlist_maker_android_romankovaekaterina.data.repository

import com.example.playlist_maker_android_romankovaekaterina.data.network.TracksSearchRequest
import com.example.playlist_maker_android_romankovaekaterina.data.network.TracksSearchResponse
import com.example.playlist_maker_android_romankovaekaterina.domain.api.NetworkClient
import com.example.playlist_maker_android_romankovaekaterina.domain.api.TracksRepository
import com.example.playlist_maker_android_romankovaekaterina.domain.models.Track
import kotlinx.coroutines.delay
import com.example.playlist_maker_android_romankovaekaterina.data.database.DatabaseMock
import kotlinx.coroutines.flow.Flow

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: DatabaseMock
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1_000)
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map { dto ->
                val totalSeconds = dto.trackTimeMillis / 1_000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                val formattedTime = "%02d:%02d".format(minutes, seconds)
                Track(
                    id = (dto.trackName + dto.artistName + formattedTime).hashCode().toLong(),
                    trackName = dto.trackName,
                    artistName = dto.artistName,
                    trackTime = formattedTime
                )
            }
        } else {
            emptyList()
        }
    }
    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return database.getTrackByNameAndArtist(track)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        database.insertTrack(track.copy(playlistId = playlistId))
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        database.insertTrack(track.copy(playlistId = 0))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.insertTrack(track.copy(favorite = isFavorite))
    }

    override fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTracksByPlaylistId(playlistId)
    }
}