package com.example.playlist_maker_android_romankovaekaterina.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_romankovaekaterina.R
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    val emailTo = stringResource(R.string.dev_email_to)
    val emailSubject = stringResource(R.string.dev_email_subject)
    val emailBody = stringResource(R.string.dev_email_body)
    val offerUrl = stringResource(R.string.offer_url)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.description_back)
                        )
                    }
                }
            )
        },
        containerColor = colorResource(R.color.white)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val rowHorizontalPadding = dimensionResource(R.dimen.settings_screen_row_padding_horizontal)
            val rowVerticalPadding = dimensionResource(R.dimen.settings_screen_row_padding_vertical)
            val rowSpacerHeight = dimensionResource(R.dimen.settings_screen_section_spacing)
            val textSize = integerResource(R.integer.settings_text_size).sp

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = context.getString(R.string.intent_type_text_plain)
                        }
                        val chooser = Intent.createChooser(
                            shareIntent,
                            context.getString(R.string.share_chooser_title)
                        )
                        context.startActivity(chooser)
                    }
                    .padding(horizontal = rowHorizontalPadding, vertical = rowVerticalPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.btn_share_app),
                    fontSize = textSize,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(rowSpacerHeight))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val mailUri = Uri.parse(
                            context.getString(R.string.mailto_scheme, emailTo)
                        )
                        val emailIntent = Intent(Intent.ACTION_SENDTO, mailUri).apply {
                            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                            putExtra(Intent.EXTRA_TEXT, emailBody)
                        }
                        context.startActivity(emailIntent)
                    }
                    .padding(horizontal = rowHorizontalPadding, vertical = rowVerticalPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.btn_write_to_devs),
                    fontSize = textSize,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(rowSpacerHeight))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offerUrl))
                        context.startActivity(intent)
                    }
                    .padding(horizontal = rowHorizontalPadding, vertical = rowVerticalPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.btn_user_agreement),
                    fontSize = textSize,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(rowSpacerHeight))
        }
    }
}
