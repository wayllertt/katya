package com.example.playlist_maker_android_romankovaekaterina

import android.os.Bundle
import androidx.compose.material3.Text
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource

class Settings_Activity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(stringResource(id = R.string.search))
        }
    }
}