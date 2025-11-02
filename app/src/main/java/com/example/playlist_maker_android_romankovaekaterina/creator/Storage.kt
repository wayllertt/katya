package com.example.playlist_maker_android_romankovaekaterina.creator

import com.example.playlist_maker_android_romankovaekaterina.data.dto.TrackDto
import java.util.Locale

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTimeMillis = 158_000
        ),
        TrackDto(
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283_000
        ),
        TrackDto(
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312_000
        ),
        TrackDto(
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225_000
        ),
        TrackDto(
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272_000
        ),
        TrackDto(
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230_000
        ),
        TrackDto(
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296_000
        ),
        TrackDto(
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195_000
        ),
        TrackDto(
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222_000
        ),
        TrackDto(
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241_000
        )
    )

    fun search(request: String): List<TrackDto> {
        val normalizedTokens = request
            .normalizeForSearch()
            .split(whitespaceRegex)
            .filter { it.isNotEmpty() }

        if (normalizedTokens.isEmpty()) return emptyList()
        return listTracks.filter { track ->
            val searchableText = listOf(track.trackName, track.artistName)
                .joinToString(separator = " ")
                .normalizeForSearch()

            normalizedTokens.all(searchableText::contains)
        }
    }

    private fun String.normalizeForSearch(): String =
        lowercase(Locale.getDefault())
            .replace('ё', 'е')
            .replace(whitespaceRegex, " ")
            .trim()

    private companion object {
        val whitespaceRegex = "\\s+".toRegex()
    }
}