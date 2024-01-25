package com.example.lastfm.albums.api

import com.example.lastfm.albums.AlbumResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsApi {
    @GET("?method=album.search&api_key=&format=json")
    suspend fun searchAlbum(
        @Query("album") album: String,
    ): Response<AlbumResponse>

}