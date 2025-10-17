package com.example.playlist_maker_android_romankovaekaterina

import android.content.Intent
import android.widget.Toast
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Preview
    @Composable
    fun MainScreen() {
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F9F9))
        ) { Box(
            modifier = Modifier
                .background(Color(0xFF3D6EFF), RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                .padding(vertical = 30.dp, horizontal = 16.dp)
                .fillMaxWidth()
        )
        {
            Text(
                text = stringResource(id = R.string.playlist_maker),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
            Spacer(modifier = Modifier.height(10.dp))
            DrawerItem(icon = Icons.Default.Search, stringResource(id = R.string.search)) {
                val intent = Intent(context, Search_Activity::class.java)
                context.startActivity(intent)
            }
            DrawerItem(icon = Icons.Default.PlayArrow, stringResource(id = R.string.playlists)) {
                Toast.makeText(context, "Кнопка нажата", Toast.LENGTH_SHORT).show()
            }
            DrawerItem(icon = Icons.Default.FavoriteBorder, stringResource(id = R.string.favorites)) {
                Toast.makeText(context, "Кнопка нажата", Toast.LENGTH_SHORT).show()
            }
            DrawerItem(icon = Icons.Default.Settings, stringResource(id = R.string.settings)) {
                val intent = Intent(context, Settings_Activity::class.java)
                context.startActivity(intent)
            }
        }
    }

    @Composable
    fun DrawerItem(
        icon: ImageVector,
        text: String,
        onClick: (() -> Unit)? = null
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick?.invoke() }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black.copy(alpha = 0.85f),
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = text,
                color = Color.Black.copy(alpha = 0.9f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}