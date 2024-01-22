package com.example.lastfm.albums

import com.example.lastfm.artists.Artist
import com.example.lastfm.artists.Artists
import com.google.gson.annotations.SerializedName

data class AlbumResponse(
    val results: Results? = null
)

data class Results(
    val albummatches: Albums? = null,
)
data class Albums(
    @SerializedName("album")
    val albums: List<Album>? = null
)