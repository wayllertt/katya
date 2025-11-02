package com.example.playlist_maker_android_romankovaekaterina.creator

import com.example.playlist_maker_android_romankovaekaterina.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_romankovaekaterina.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_romankovaekaterina.domain.api.TracksRepository

object Creator {
    private val storage by lazy { Storage() }
    private val networkClient by lazy { RetrofitNetworkClient(storage) }

    fun provideTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(networkClient)
    }
}
