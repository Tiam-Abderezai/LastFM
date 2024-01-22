package com.example.lastfm.artists.api

import com.example.berlingo.data.network.Resource
import com.example.lastfm.artists.ArtistsResponse
import com.example.lastfm.artists.top_albums.TopAlbumsResponse
import com.example.lastfm.artists.top_albums.top_tracks.TopTracksResponse
import com.example.lastfm.common.utils.logger.BaseLogger
import com.example.lastfm.common.utils.logger.FactoryLogger
import javax.inject.Inject

private val logger: BaseLogger = FactoryLogger.getLoggerKClass(ArtistsApiImpl::class)

class ArtistsApiImpl @Inject constructor(
    private val artistsApi: ArtistsApi,
) : ArtistsRepository {

    override suspend fun searchArtists(artist: String): Resource<ArtistsResponse> {
        return try {
            val response = artistsApi.searchArtists(artist = artist)

            if (response.isSuccessful) {
                logger.debug("${response.body()?.results?.artistmatches}")
                logger.debug(response.message())
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                logger.debug(response.message())
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
            Resource.error("${e.message}", null)
        }
    }

    override suspend fun getTopAlbums(artist: String): Resource<TopAlbumsResponse> {
        return try {
            val response = artistsApi.getTopAlbums(artist = artist)

            if (response.isSuccessful) {
                logger.debug("${response.body()?.topAlbums}")
                logger.debug(response.message())
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                logger.debug(response.message())
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
            Resource.error("${e.message}", null)
        }
    }

    override suspend fun getTopTracks(artist: String): Resource<TopTracksResponse> {
        return try {
            val response = artistsApi.getTracks(artist = artist)

            if (response.isSuccessful) {
                logger.debug("${response.body()?.topTracks?.tracks}")
                logger.debug(response.message())
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                logger.debug(response.message())
                Resource.error("An unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
            Resource.error("${e.message}", null)
        }
    }
}
