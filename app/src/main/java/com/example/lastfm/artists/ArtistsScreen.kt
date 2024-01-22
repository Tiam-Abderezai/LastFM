package com.example.lastfm.artists

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.lastfm.R
import com.example.lastfm.artists.ArtistsState.*
import com.example.lastfm.common.utils.ErrorScreen
import com.example.lastfm.common.utils.LoadingScreen
import com.example.lastfm.common.utils.logger.BaseLogger
import com.example.lastfm.common.utils.logger.FactoryLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val logger: BaseLogger = FactoryLogger.getLoggerCompose("ArtistsScreen()")
private var queryText by mutableStateOf("")

@Composable
fun ArtistsScreen(
    navController: NavHostController,
    artistsState: ArtistsState,
    artistsEvent: suspend (event: ArtistsEvent) -> Unit,
) {
    Column {
        SearchArtists(artistsEvent)
        when (artistsState) {
            is Initial -> {}
            is Loading -> {
                LoadingScreen()
            }

            is Success -> {
                DisplayArtists(navController, artists = artistsState.artists, artistsEvent)
            }

            is Error -> {
                ErrorScreen(message = artistsState.message)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchArtists(artistsEvent: suspend (event: ArtistsEvent) -> Unit) {
//    var query = text
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = queryText,
        onValueChange = { query ->
            queryText = query
        },
        label = { Text("Enter text") },
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(64.dp),
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    artistsEvent.invoke(ArtistsEvent.SearchArtist(queryText))
                }
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_search),
                contentDescription = stringResource(R.string.content_description_button_search_artist),
            )
        }
    }
}

@Composable
private fun DisplayArtists(
    navController: NavHostController,
    artists: Artists?,
    artistsEvent: suspend (event: ArtistsEvent) -> Unit,
) {
    LazyColumn {
        items(artists?.artists ?: emptyList()) { artist ->
            val name = artist.name
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("top_albums")
                        CoroutineScope(Dispatchers.IO).launch {
                            artistsEvent.invoke(
                                ArtistsEvent.SearchTopAlbums(
                                    name ?: "",
                                ),
                            )
                        }
                    },
            ) {
                ArtistItem(artist)
            }
        }
    }
}

@Composable
fun ArtistItem(artist: Artist) {
    val name = artist.name
     Text(text = name ?: "", style = MaterialTheme.typography.headlineMedium)
}

// @Preview(showBackground = true, name = "Main Screen Preview")
// @Composable
// fun PreviewArtistsScreen() {
//    ArtistsScreen(artistsState, artistsEvent)
// }
