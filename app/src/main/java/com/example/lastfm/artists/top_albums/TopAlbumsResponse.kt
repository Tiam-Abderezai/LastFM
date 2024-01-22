package com.example.lastfm.artists.top_albums

import com.example.lastfm.albums.Album
import com.google.gson.annotations.SerializedName

data class TopAlbumsResponse(
    @SerializedName("topalbums")
    val topAlbums: TopAlbums? = null,
)
data class TopAlbums(
    @SerializedName("album")
    val albums: List<Album>? = null,
)
