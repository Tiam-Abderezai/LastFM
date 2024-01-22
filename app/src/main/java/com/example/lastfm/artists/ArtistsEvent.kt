package com.example.lastfm.artists

sealed class ArtistsEvent {
    data class SearchArtist(
        val artist: String,
    ) : ArtistsEvent()
    data class SearchTopAlbums(
        val artist: String,
    ) : ArtistsEvent()
    data class SearchTopTracks(
        val artist: String,
    ) : ArtistsEvent()
}
