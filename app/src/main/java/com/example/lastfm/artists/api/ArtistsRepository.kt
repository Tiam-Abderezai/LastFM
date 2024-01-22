package com.example.lastfm.artists.api

import com.example.berlingo.data.network.Resource
import com.example.lastfm.artists.ArtistsResponse
import com.example.lastfm.artists.top_albums.TopAlbumsResponse
import com.example.lastfm.artists.top_albums.top_tracks.TopTracksResponse

interface ArtistsRepository {
    suspend fun searchArtists(artist: String): Resource<ArtistsResponse>
    suspend fun getTopAlbums(artist: String): Resource<TopAlbumsResponse>
    suspend fun getTopTracks(artist: String): Resource<TopTracksResponse>
}
