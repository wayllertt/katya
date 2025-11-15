package com.example.playlist_maker_android_romankovaekaterina.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_romankovaekaterina.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource

@Composable
fun MainScreen(
    onOpenSearch: () -> Unit,
    onOpenPlaylists: () -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenSettings: () -> Unit,
) {

    val screenBackground = colorResource(R.color.background)
    val contentSpacing = dimensionResource(R.dimen.main_screen_content_spacing)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackground)

    ) {
        Header(title = stringResource(id = R.string.playlist_maker))

        Spacer(Modifier.height(contentSpacing))

        MenuRow(icon = Icons.Default.Search, text = stringResource(R.string.search)) {
            onOpenSearch()
        }
        MenuRow(icon = Icons.Default.PlayArrow, text = stringResource(R.string.playlists)) {
            onOpenPlaylists()
        }
        MenuRow(icon = Icons.Default.FavoriteBorder, text = stringResource(R.string.favorites)) {
            onOpenFavorites()
        }
        MenuRow(icon = Icons.Default.Settings, text = stringResource(R.string.settings_title)) {
            onOpenSettings()
        }





    }
}

@Composable
private fun Header(title: String) {

    val headerBackground = colorResource(R.color.main_screen_header_background)
    val headerHorizontalPadding = dimensionResource(R.dimen.main_screen_header_padding_horizontal)
    val headerVerticalPadding = dimensionResource(R.dimen.main_screen_header_padding_vertical)
    val headerCornerRadius = dimensionResource(R.dimen.main_screen_header_corner_radius)
    val headerTextColor = colorResource(R.color.white)
    val headerTextSize = integerResource(R.integer.main_screen_header_text_size).sp

    Box(
        modifier = Modifier
            .background(
                color = headerBackground,
                shape = RoundedCornerShape(
                    bottomStart = headerCornerRadius,
                    bottomEnd = headerCornerRadius
                )
            )
            .fillMaxWidth()
            .padding(horizontal = headerHorizontalPadding, vertical = headerVerticalPadding)
    ) {
        Text(
            text = title,
            color = headerTextColor,
            fontSize = headerTextSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MenuRow(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null
) {
    val click = rememberUpdatedState(newValue = onClick)
    val itemHorizontalPadding = dimensionResource(R.dimen.main_screen_menu_item_padding_horizontal)
    val itemVerticalPadding = dimensionResource(R.dimen.main_screen_menu_item_padding_vertical)
    val iconTint = colorResource(R.color.main_screen_menu_icon_tint)
    val iconSize = dimensionResource(R.dimen.main_screen_menu_icon_size)
    val textSpacing = dimensionResource(R.dimen.main_screen_menu_icon_spacing)
    val textColor = colorResource(R.color.main_screen_menu_text_color)
    val textSize = integerResource(R.integer.main_screen_menu_text_size).sp


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { click.value?.invoke() }
            .padding(horizontal = itemHorizontalPadding, vertical = itemVerticalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )

        Spacer(Modifier.width(20.dp))

        Text(
            text = text,
            color = textColor,
            fontSize = textSize,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
    }
}
