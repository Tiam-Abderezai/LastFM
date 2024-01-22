package com.example.lastfm

sealed class Screen(val route: String) {
    object Albums : Screen(route = "albums")
    object Artists : Screen(route = "artists")
    object TopAlbums : Screen(route = "top_albums")
    object TopTracks : Screen(route = "top_tracks")

}