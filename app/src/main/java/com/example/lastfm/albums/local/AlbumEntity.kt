package com.example.lastfm.albums.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lastfm.common.Constants.Companion.TABLE_ALBUM
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_ALBUM)
@Parcelize
data class AlbumEntity(
    @PrimaryKey
    val mBid: String,
    val name: String,
    val url: String
) : Parcelable