package com.example.lastfm.albums.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AlbumEntity::class], version = 1)
@TypeConverters(AlbumsConverter::class)
abstract class AlbumsDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}