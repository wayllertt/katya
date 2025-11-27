package com.example.playlist_maker_android_romankovaekaterina.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_romankovaekaterina.R
import com.example.playlist_maker_android_romankovaekaterina.domain.models.Track
import com.example.playlist_maker_android_romankovaekaterina.ui.playlists.PlaylistViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    trackName: String,
    artistName: String,
    trackTime: String,
    onBack: () -> Unit,
    playlistViewModel: PlaylistViewModel
) {
    val backgroundColor = colorResource(R.color.white)
    val playlists by playlistViewModel.playlists.collectAsState(initial = emptyList())
    val favorites by playlistViewModel.favoriteList.collectAsState(initial = emptyList())
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    var isFavorite by remember { mutableStateOf(false) }
    val track = remember(trackName, artistName, trackTime) {
        Track(
            id = (trackName + artistName + trackTime).hashCode().toLong(),
            trackName = trackName,
            artistName = artistName,
            trackTime = trackTime
        )
    }

    LaunchedEffect(favorites, track) {
        isFavorite = favorites.any { it.trackName == track.trackName && it.artistName == track.artistName }
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(onDismissRequest = { isBottomSheetVisible = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = stringResource(id = R.string.add_to_playlist_title), fontWeight = FontWeight.Bold)
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(playlists) { playlist ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        val existed = playlistViewModel.isExist(track)
                                        playlistViewModel.insertTrackToPlaylist(existed ?: track, playlist.id)
                                        isBottomSheetVisible = false
                                    }
                                }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(playlist.name, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.track_details_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.description_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Text(text = track.trackName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = track.artistName, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.track_length, track.trackTime))

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        playlistViewModel.toggleFavorite(track, !isFavorite)
                        isFavorite = !isFavorite
                    }
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.add_to_favorites_icon)
                    )
                }

                IconButton(onClick = { isBottomSheetVisible = true }) {
                    Icon(
                        imageVector = Icons.Filled.LibraryAdd,
                        contentDescription = stringResource(id = R.string.add_to_playlist_icon)
                    )
                }
            }
        }
    }
}