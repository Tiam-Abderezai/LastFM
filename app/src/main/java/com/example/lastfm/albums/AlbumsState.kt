package com.example.lastfm.albums

sealed class AlbumsState {
    object Initial : AlbumsState()
    object Loading : AlbumsState()
    data class Success(val album: Album) : AlbumsState()
    data class Error(val message: String) : AlbumsState()
}