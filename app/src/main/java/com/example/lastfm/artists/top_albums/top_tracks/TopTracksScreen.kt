package com.example.lastfm.artists.top_albums.top_tracks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lastfm.R
import com.example.lastfm.artists.ArtistsEvent
import com.example.lastfm.artists.ArtistsState
import com.example.lastfm.artists.ArtistsState.*
import com.example.lastfm.common.utils.ErrorScreen
import com.example.lastfm.common.utils.LoadingScreen
import com.example.lastfm.common.utils.logger.BaseLogger
import com.example.lastfm.common.utils.logger.FactoryLogger

private val logger: BaseLogger = FactoryLogger.getLoggerCompose("TopTracksScreen()")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopTracksScreen(
    navController: NavHostController,
    artistsState: ArtistsState,
    artistsEvent: suspend (event: ArtistsEvent) -> Unit,
) {
    when (artistsState) {
        is Initial -> {}
        is Loading -> {
            LoadingScreen()
        }
        is Success -> {
            Column() {
                DisplayTopAppBar(navController, artistsState.topTracks, artistsState.trackName)
                DisplayTracks(tracks = artistsState.topTracks)
            }
        }

        is ArtistsState.Error -> {
            ErrorScreen(message = artistsState.message ?: "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayTopAppBar(
    navController: NavHostController,
    topTracks: List<Track>?,
    trackName: String?
) {
    val artistName = topTracks?.get(0)?.artist?.name ?: ""
    TopAppBar(
        title = { Text(trackName ?: "") },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.content_description_back),
                )
            }
        },
    )
}

@Composable
fun DisplayTracks(tracks: List<Track>?) {
    LazyColumn {
        items(tracks ?: emptyList()) { track ->
            TopTrackItem(track)
        }
    }
}

@Composable
fun TopTrackItem(track: Track) {
    val trackName = track.name
    val trackImage = track.images?.get(0)
    val artistName = track.artist
    logger.debug(trackName)
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = trackName, style = MaterialTheme.typography.headlineMedium)
        Text(text = "by $artistName", style = MaterialTheme.typography.titleSmall)
    }
}
