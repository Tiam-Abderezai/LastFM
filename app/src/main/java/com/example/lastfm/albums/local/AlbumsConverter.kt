package com.example.lastfm.albums.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class AlbumsConverter {
    @TypeConverter
    fun fromAlbum(album: AlbumEntity) = Gson().toJson(album)

    @TypeConverter
    fun toAlbum(s: String) = Gson().fromJson(s, AlbumEntity::class.java)

}