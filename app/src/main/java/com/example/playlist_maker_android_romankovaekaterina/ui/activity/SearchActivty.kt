package com.example.playlist_maker_android_romankovaekaterina.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.playlist_maker_android_romankovaekaterina.ui.search.SearchRoute

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchRoute(onBack = { finish() })
        }
    }
}