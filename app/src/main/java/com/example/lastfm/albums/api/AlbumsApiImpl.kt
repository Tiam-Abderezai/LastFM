package com.example.lastfm.albums.api

import android.util.Log
import com.example.berlingo.data.network.Resource
import com.example.lastfm.albums.AlbumResponse
import com.example.lastfm.artists.api.ArtistsApiImpl
import com.example.lastfm.common.utils.logger.BaseLogger
import com.example.lastfm.common.utils.logger.FactoryLogger
import javax.inject.Inject

private val logger: BaseLogger = FactoryLogger.getLoggerKClass(ArtistsApiImpl::class)
class AlbumsApiImpl @Inject constructor(
    private val albumsApi: AlbumsApi,
) : AlbumsApiRepository {

    override suspend fun searchAlbum(album: String): Resource<AlbumResponse> {
        return try {
            val response = albumsApi.searchAlbum(album = album)

            if (response.isSuccessful) {
                logger.debug("${response.body()}")
                logger.debug(response.message())
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
            Resource.error("${e.message}", null)
        }
    }
}