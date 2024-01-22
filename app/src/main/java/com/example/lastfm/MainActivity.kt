package com.example.lastfm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lastfm.albums.AlbumsScreen
import com.example.lastfm.albums.AlbumsViewModel
import com.example.lastfm.artists.ArtistsScreen
import com.example.lastfm.artists.ArtistsViewModel
import com.example.lastfm.artists.top_albums.TopAlbumsScreen
import com.example.lastfm.artists.top_albums.top_tracks.TopTracksScreen
import com.example.lastfm.ui.theme.LastFMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LastFMTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
    ) {
        NavigationHost(navController = navController)
    }
}

@Composable
private fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val currentRoute = currentRoute(navController)

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_album),
                    contentDescription = null,
                )
            },
            selected = currentRoute == "albums",
            onClick = { navController.navigate("albums") },
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_artist),
                    contentDescription = null,
                )
            },
            selected = currentRoute == "artists",
            onClick = { navController.navigate("artists") },
        )

//        NavigationBarItem(
//            icon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.icon_top_album),
//                    contentDescription = null,
//                )
//            },
//            selected = currentRoute == "top_album",
//            onClick = { navController.navigate("top_album") },
//        )
    }
}

@Composable
private fun NavigationHost(navController: NavHostController) {
    val artistsViewModel = hiltViewModel<ArtistsViewModel>()
    val artistsState = artistsViewModel.state.collectAsState().value
    val artistsEvent = artistsViewModel::handleEvent

    val albumsViewModel = hiltViewModel<AlbumsViewModel>()
    val albumsState = albumsViewModel.state.collectAsState().value
    val albumsEvent = albumsViewModel::handleEvent

    NavHost(navController, startDestination = Screen.Albums.route) {
        composable(Screen.Albums.route) { AlbumsScreen() }
        composable(Screen.Artists.route) { ArtistsScreen(navController, artistsState, artistsEvent) }
        composable(Screen.TopAlbums.route) { TopAlbumsScreen(navController, artistsState, artistsEvent, albumsState, albumsEvent) }
        composable(Screen.TopTracks.route) { TopTracksScreen(navController, artistsState, artistsEvent) }
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview(showBackground = true, name = "Main Screen Preview")
@Composable
fun PreviewMainScreen() {
    MainScreen()
}
