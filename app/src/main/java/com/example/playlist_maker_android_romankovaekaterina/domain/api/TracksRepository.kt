package com.example.playlist_maker_android_romankovaekaterina.domain.api

import com.example.playlist_maker_android_romankovaekaterina.domain.models.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}
