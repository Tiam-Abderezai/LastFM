package com.example.lastfm.common

import com.example.lastfm.BuildConfig

class Constants {
    companion object {
        // Retrofit Constants
        const val API_KEY = BuildConfig.API_KEY
//        const val API_SECRET = BuildConfig.API_SECRET
//        const val BASE_URL = BuildConfig.BASE_URL
        const val SEARCH_NEWS_TIME_DELAY = 500L

        // Room Constants
        const val DATABASE_NAME = "last_fm_db"
        const val TABLE_ALBUM = "table_album"

    }
}