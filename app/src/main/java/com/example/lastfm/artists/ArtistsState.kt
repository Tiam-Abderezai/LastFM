package com.example.lastfm.artists

import com.example.lastfm.albums.Album
import com.example.lastfm.artists.top_albums.top_tracks.Track

sealed class ArtistsState {
    object Initial : ArtistsState()
    object Loading : ArtistsState()
    data class Success(
        val artists: Artists? = null,
        val trackName: String? = "",
        val topAlbums: List<Album>? = null,
        val topTracks: List<Track>? = null,
    ) : ArtistsState()

    data class Error(val message: String) : ArtistsState()
}
