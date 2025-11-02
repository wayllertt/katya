package com.example.playlist_maker_android_romankovaekaterina.domain.api

import com.example.playlist_maker_android_romankovaekaterina.data.network.BaseResponse

interface NetworkClient {
    fun doRequest(dto: Any): BaseResponse
}
