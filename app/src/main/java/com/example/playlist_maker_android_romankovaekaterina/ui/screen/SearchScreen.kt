package com.example.playlist_maker_android_romankovaekaterina.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_romankovaekaterina.R
import com.example.playlist_maker_android_romankovaekaterina.creator.Creator
import com.example.playlist_maker_android_romankovaekaterina.domain.models.Track
import com.example.playlist_maker_android_romankovaekaterina.ui.search.SearchError
import com.example.playlist_maker_android_romankovaekaterina.ui.search.SearchState
import com.example.playlist_maker_android_romankovaekaterina.ui.search.SearchViewModel
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.TextStyle

@Composable
fun SearchRoute(
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    viewModel: SearchViewModel = viewModel(factory = Creator.provideSearchViewModelFactory()),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchScreen(
        state = state,
        onBack = onBack,
        onSearch = viewModel::search,
        onReset = viewModel::reset,
        onTrackClick = onTrackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onBack: () -> Unit,
    onSearch: (String) -> Unit,
    onReset: () -> Unit,
    onTrackClick: (Track) -> Unit,
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val fieldColor = MaterialTheme.colorScheme.surfaceVariant
    val context = LocalContext.current
    val backgroundColor = colorResource(R.color.white)
    val textColor = colorResource(R.color.black)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.search_title),
                        fontWeight = FontWeight.Medium,
                        color = textColor,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.description_back),
                            tint = textColor,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = textColor,
                    navigationIconContentColor = textColor,
                ),
            )
        },
        containerColor = backgroundColor,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(backgroundColor)
                .padding(horizontal = dimensionResource(R.dimen.search_screen_horizontal_padding)),
        ) {

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.search_screen_top_spacing)))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    searchQuery = newValue
                    if (newValue.isBlank()) {
                        onReset()
                    }
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.placeholder),
                        color = textColor,
                    )
                },
                leadingIcon = {
                    IconButton(
                        onClick = { onSearch(searchQuery) },
                        enabled = searchQuery.isNotBlank(),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(id = R.string.description_search),
                            tint = textColor,
                        )
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            onReset()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(id = R.string.description_clear),
                                tint = textColor,
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(dimensionResource(R.dimen.search_screen_field_corner_radius)),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = fieldColor,
                    unfocusedContainerColor = fieldColor,
                    disabledContainerColor = fieldColor,
                    errorContainerColor = fieldColor,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                ),
                textStyle = TextStyle(color = textColor),
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.search_screen_content_spacing)))

            when (state) {
                SearchState.Initial -> Unit
                SearchState.Searching -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(R.dimen.search_screen_status_top_padding)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.search_screen_loading_spacing)))
                        Text(
                            text = stringResource(id = R.string.loading),
                            color = textColor,
                        )
                    }
                }

                is SearchState.Fail -> {
                    val message = when (state.reason) {
                        SearchError.EMPTY_RESULT -> R.string.error_empty
                        SearchError.NETWORK -> R.string.error_network
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimensionResource(R.dimen.search_screen_status_top_padding)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(id = message),
                            color = textColor,
                        )
                    }
                }

                is SearchState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            bottom = dimensionResource(R.dimen.search_screen_list_bottom_padding)
                        ),
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionResource(R.dimen.search_screen_list_item_spacing)
                        ),
                    ) {
                        items(state.tracks) { track ->
                            TrackRow(
                                track = track,
                                onClick = { onTrackClick(track) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TrackRow(
    track: Track,
    onClick: () -> Unit,
) {
    val backgroundColor = colorResource(R.color.white)
    val textColor = colorResource(R.color.black)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(
                    dimensionResource(R.dimen.search_screen_track_corner_radius)
                ),
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = dimensionResource(R.dimen.search_screen_track_padding_horizontal),
                vertical = dimensionResource(R.dimen.search_screen_track_padding_vertical)
            ),
    ) {
        RowWithTime(
            title = track.trackName,
            time = track.trackTime,
            textColor = textColor,
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.search_screen_track_spacing)))
        Text(
            text = track.artistName,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun RowWithTime(
    title: String,
    time: String,
    textColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
        )
    }
}
