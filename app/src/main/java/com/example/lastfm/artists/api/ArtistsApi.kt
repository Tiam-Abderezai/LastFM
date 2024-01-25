package com.example.lastfm.artists.api

import com.example.lastfm.artists.ArtistsResponse
import com.example.lastfm.artists.top_albums.TopAlbumsResponse
import com.example.lastfm.artists.top_albums.top_tracks.TopTracksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArtistsApi {
    @GET("?method=artist.search&api_key=&format=json")
    suspend fun searchArtists(
        @Query("artist") artist: String,
    ): Response<ArtistsResponse>

    @GET("?method=artist.gettopalbums&api_key=&format=json&format=json")
    suspend fun getTopAlbums(
        @Query("artist") artist: String
    ): Response<TopAlbumsResponse>

    @GET("?method=artist.gettoptracks&artist&api_key=&format=json&format=json")
    suspend fun getTracks(
        @Query("artist") artist: String
    ): Response<TopTracksResponse>
}
