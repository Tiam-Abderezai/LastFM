package com.example.lastfm.artists.top_albums.top_tracks

import com.example.lastfm.albums.Image
import com.example.lastfm.artists.Artist
import com.google.gson.annotations.SerializedName
import java.io.Serial

data class TopTracksResponse(
    @SerializedName("toptracks")
    val topTracks: TopTracks
)

data class TopTracks(
    val tracksId: String,
    @SerializedName("track")
    val tracks: List<Track>?
)

data class Track(
    val name: String,
    @SerializedName("playcount")
    val playCount: Int,
    val listeners: Int,
    val artist: Artist,
    val url: String,
    @SerializedName("image")
    val images: List<Image>?
)