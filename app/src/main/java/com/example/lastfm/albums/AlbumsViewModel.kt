package com.example.lastfm.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lastfm.albums.api.AlbumsApiImpl
import com.example.lastfm.albums.local.AlbumEntity
import com.example.lastfm.albums.local.AlbumsDBImpl
import com.example.lastfm.common.utils.logger.BaseLogger
import com.example.lastfm.common.utils.logger.FactoryLogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private val logger: BaseLogger = FactoryLogger.getLoggerKClass(AlbumsViewModel::class)

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val albumsApiImpl: AlbumsApiImpl,
    private val albumsDBImpl: AlbumsDBImpl,
) : ViewModel() {
    private val _state = MutableStateFlow<AlbumsState>(AlbumsState.Initial)
    val state: StateFlow<AlbumsState> = _state.asStateFlow()

    suspend fun handleEvent(event: AlbumsEvent) {
        when (event) {
            is AlbumsEvent.SearchAlbum -> {
                searchAlbum(event.albumName)
            }
            is AlbumsEvent.LikeAlbum -> {
                saveTopAlbum(event.album)
            }
            is AlbumsEvent.UnlikeAlbum -> {
                deleteTopAlbum(event.album)
            }
        }
    }

    private suspend fun searchAlbum(album: String) {
        logger.debug("album text: $album")

        try {
            _state.value = AlbumsState.Loading
            val response = albumsApiImpl.searchAlbum(
                album = album,
            )
            val album = response.data
            logger.debug("${response.data}")

//            _state.value = AlbumsState.Success(album = album)
        } catch (e: Exception) {
            _state.value = AlbumsState.Error(message = e.message ?: "Unknown Error")
        }
    }

    private fun saveTopAlbum(album: Album) {
        val albumEntity = AlbumEntity(
            mBid = album.mBid ?: "",
            name = album.name ?: "",
            url = album.url ?: "",
        )
        viewModelScope.launch(Dispatchers.IO) {
            albumsDBImpl.insertAlbum(albumEntity)
            logger.debug("Album Entity: $albumEntity")
        }
    }

    private fun deleteTopAlbum(album: Album) {
        val albumEntity = AlbumEntity(
            mBid = album.mBid ?: "",
            name = album.name ?: "",
            url = album.url ?: "",
        )
        viewModelScope.launch(Dispatchers.IO) {
            albumsDBImpl.deleteAlbum(albumEntity)
            logger.debug("Album Entity: $albumEntity")
        }
    }
}
