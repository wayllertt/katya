package com.example.playlist_maker_android_romankovaekaterina.data.network

import com.example.playlist_maker_android_romankovaekaterina.creator.Storage
import com.example.playlist_maker_android_romankovaekaterina.domain.api.NetworkClient

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(dto: Any): TracksSearchResponse {
        val request = dto as TracksSearchRequest
        val searchList = storage.search(request.expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}