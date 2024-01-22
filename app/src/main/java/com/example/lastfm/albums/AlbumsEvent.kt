package com.example.lastfm.albums

sealed class AlbumsEvent {
    data class SearchAlbum(
        val albumName: String,
    ) : AlbumsEvent()
    data class LikeAlbum(
        val album: Album,
    ) : AlbumsEvent()
    data class UnlikeAlbum(
        val album: Album,
    ) : AlbumsEvent()
//    data class SearchTopAlbums(
//        val artist: String,
//    ) : AlbumsEvent()
//    data class SearchTopTracks(
//        val artist: String,
//    ) : AlbumsEvent()
}