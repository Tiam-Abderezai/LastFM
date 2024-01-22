package com.example.lastfm.artists

import androidx.lifecycle.ViewModel
import com.example.lastfm.artists.api.ArtistsApiImpl
import com.example.lastfm.common.utils.logger.BaseLogger
import com.example.lastfm.common.utils.logger.FactoryLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private val logger: BaseLogger = FactoryLogger.getLoggerKClass(ArtistsViewModel::class)

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    private val artistsApiImpl: ArtistsApiImpl,
) : ViewModel() {
    private val _state = MutableStateFlow<ArtistsState>(ArtistsState.Initial)
    val state: StateFlow<ArtistsState> = _state.asStateFlow()

    suspend fun handleEvent(event: ArtistsEvent) {
        when (event) {
            is ArtistsEvent.SearchArtist -> {
                searchArtist(event.artist)
            }
            is ArtistsEvent.SearchTopAlbums -> {
                getTopAlbums(event.artist)
            }
            is ArtistsEvent.SearchTopTracks -> {
                getTopTracks(event.artist)
            }
        }
    }

    private suspend fun searchArtist(artist: String) {
        logger.debug("query text: $artist")

        try {
            _state.value = ArtistsState.Loading
            val response = artistsApiImpl.searchArtists(
                artist = artist,
            )
            val artists = response.data?.results?.artistmatches
            logger.debug("${response.data?.results}")

            _state.value = ArtistsState.Success(artists = artists)
        } catch (e: Exception) {
            _state.value = ArtistsState.Error(message = e.message ?: "Unknown Error")
        }
    }

    private suspend fun getTopAlbums(artist: String) {
        logger.debug("query text: $artist")

        try {
            _state.value = ArtistsState.Loading
            val response = artistsApiImpl.getTopAlbums(artist = artist)
            val topAlbums = response.data?.topAlbums
            logger.debug("${response.data?.topAlbums}")

            _state.value = ArtistsState.Success(topAlbums = topAlbums?.albums)
        } catch (e: Exception) {
            _state.value = ArtistsState.Error(message = e.message ?: "Unknown Error")
        } }

    private suspend fun getTopTracks(artist: String) {
        logger.debug("query text: $artist")

        try {
            _state.value = ArtistsState.Loading
            val response = artistsApiImpl.getTopTracks(artist = artist)
            val tracks = response.data?.topTracks?.tracks
            logger.debug("$tracks")

            _state.value = ArtistsState.Success(topTracks = tracks)
        } catch (e: Exception) {
            _state.value = ArtistsState.Error(message = e.message ?: "Unknown Error")
        } }
}
