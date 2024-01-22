package com.example.lastfm.artists.top_albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.lastfm.R
import com.example.lastfm.albums.Album
import com.example.lastfm.albums.AlbumsEvent
import com.example.lastfm.albums.AlbumsState
import com.example.lastfm.artists.ArtistsEvent
import com.example.lastfm.artists.ArtistsState
import com.example.lastfm.artists.ArtistsState.*
import com.example.lastfm.common.utils.ErrorScreen
import com.example.lastfm.common.utils.LoadingScreen
import com.example.lastfm.common.utils.logger.BaseLogger
import com.example.lastfm.common.utils.logger.FactoryLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private val logger: BaseLogger = FactoryLogger.getLoggerCompose("TopAlbumsScreen()")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAlbumsScreen(
    navController: NavHostController,
    artistsState: ArtistsState,
    artistsEvent: suspend (event: ArtistsEvent) -> Unit,
    albumsState: AlbumsState,
    albumsEvent: suspend (event: AlbumsEvent) -> Unit,
) {
    when (artistsState) {
        is Initial -> {}
        is Loading -> {
            LoadingScreen()
        }

        is Success -> {
            Column {
                DisplayTopAppBar(navController, artistsState.topAlbums)
                DisplayTopAlbums(
                    navController,
                    topAlbums = artistsState.topAlbums,
                    artistsEvent,
                    albumsEvent,
                )
            }
        }

        is ArtistsState.Error -> {
            ErrorScreen(message = artistsState.message ?: "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayTopAppBar(navController: NavHostController, topAlbums: List<Album>?) {
    val artistName = topAlbums?.get(0)?.artist?.name ?: ""
    TopAppBar(
        title = { Text(artistName) },
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
private fun DisplayTopAlbums(
    navController: NavHostController,
    topAlbums: List<Album>?,
    artistsEvent: suspend (event: ArtistsEvent) -> Unit,
    albumsEvent: suspend (event: AlbumsEvent) -> Unit,
) {
    val album = topAlbums?.get(0)
    val albumName = topAlbums?.get(0)?.name
    logger.debug("albumName: $albumName")
    LazyColumn {
        items(topAlbums ?: emptyList()) { topAlbum ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("top_tracks")
                        CoroutineScope(Dispatchers.IO).launch {
                            artistsEvent.invoke(
                                ArtistsEvent.SearchTopTracks(
                                    albumName ?: "",
                                ),
                            )
                            albumsEvent.invoke(AlbumsEvent.LikeAlbum(album ?: Album()))
                        }
                    },
            ) {
                TopAlbumItem(topAlbum = topAlbum)
            }
        }
    }
}

@Composable
fun TopAlbumItem(topAlbum: Album) {
    val albumName = topAlbum.name ?: ""
    val albumImage = topAlbum.images?.get(3)?.text
    val artistName = topAlbum.artist.name
    logger.debug(albumName)
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(albumImage)
            .size(Size.ORIGINAL)
            .build(),
    )
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = albumName, style = MaterialTheme.typography.headlineMedium)
        Text(text = "by $artistName", style = MaterialTheme.typography.titleSmall)
        Image(painter = painter, contentDescription = null)
    }
}
