package com.example.playlist_maker_android_romankovaekaterina.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

class PlaylistsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaylistsScreen(onBack = { finish() })
        }
    }
}

@Composable
fun PlaylistsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
}