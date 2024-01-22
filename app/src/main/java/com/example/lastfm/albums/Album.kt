package com.example.lastfm.albums

import com.example.lastfm.artists.Artist
import com.google.gson.annotations.SerializedName

data class Album(
    val name: String? = "",
    val artist: Artist = Artist(),
    @SerializedName("image") val images: List<Image>? = emptyList(),
    @SerializedName("mbid") val mBid: String? = "",
    val url: String? = ""
)

data class Image(
    @SerializedName("#text")
    val text: String,
    val size: String
)