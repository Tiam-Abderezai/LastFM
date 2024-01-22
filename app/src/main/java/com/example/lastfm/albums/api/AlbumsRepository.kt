package com.example.lastfm.albums.api

import com.example.berlingo.data.network.Resource
import com.example.lastfm.albums.AlbumResponse
import com.example.lastfm.albums.local.AlbumEntity
import kotlinx.coroutines.flow.Flow

interface AlbumsApiRepository {
    suspend fun searchAlbum(album: String): Resource<AlbumResponse>
}