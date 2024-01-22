package com.example.lastfm.albums.api

import com.example.lastfm.albums.AlbumResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsApi {
    @GET("?method=album.search&api_key=6f8c9f3ce1eba7779350b7880f8af850&format=json")
    suspend fun searchAlbum(
        @Query("album") album: String,
    ): Response<AlbumResponse>

}