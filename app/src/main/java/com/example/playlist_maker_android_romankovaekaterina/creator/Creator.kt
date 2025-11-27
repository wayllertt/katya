package com.example.playlist_maker_android_romankovaekaterina.creator

import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android_romankovaekaterina.data.database.DatabaseMock
import com.example.playlist_maker_android_romankovaekaterina.data.repository.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_romankovaekaterina.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_romankovaekaterina.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_romankovaekaterina.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_romankovaekaterina.domain.api.TracksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object Creator {
    private val storage by lazy { Storage() }
    private val networkClient by lazy { RetrofitNetworkClient(storage) }
    private val scope by lazy { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    private val database by lazy { DatabaseMock(scope) }
    private val playlistsRepository by lazy { PlaylistsRepositoryImpl(database) }
    private val tracksRepository by lazy { TracksRepositoryImpl(networkClient, database) }

    fun provideTracksRepository(): TracksRepository {
        return tracksRepository
    }

    fun providePlaylistsRepository(): PlaylistsRepository {
        return playlistsRepository
    }

    fun provideDatabase(): DatabaseMock {
        return database
    }
    fun provideSearchViewModelFactory(): ViewModelProvider.Factory {
        return SearchViewModelFactory(provideTracksRepository())
    }

    fun providePlaylistViewModelFactory(): ViewModelProvider.Factory {
        return PlaylistViewModelFactory(
            providePlaylistsRepository(),
            provideTracksRepository(),
            provideDatabase()
        )
    }
}
