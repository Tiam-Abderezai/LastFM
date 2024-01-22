package com.example.lastfm.albums

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlbumsScreen() {
    LazyColumn {
//        items(sampleAlbums) { album ->
//            AlbumItem(album)
//        }
    }
}

@Composable
fun AlbumItem(album: Album) {
    Column(modifier = Modifier.padding(16.dp)) {
//        Text(text = album.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = "by ${album.artist}", style = MaterialTheme.typography.titleSmall)
    }
}
