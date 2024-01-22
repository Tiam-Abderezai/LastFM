package com.example.lastfm.albums.local

import kotlinx.coroutines.flow.Flow

interface AlbumsDBRepository {
    fun queryAlbumEntities(): Flow<List<AlbumEntity>>
    fun doesAlbumExist(mBid: String?): Boolean
    fun queryAlbumEntity(mBid: String?): AlbumEntity
    suspend fun insertAlbum(album: AlbumEntity)
    suspend fun deleteAlbum(album: AlbumEntity)
}
