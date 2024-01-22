package com.example.lastfm.di

import android.content.Context
import androidx.room.Room
import com.example.lastfm.albums.api.AlbumsApi
import com.example.lastfm.albums.local.AlbumsDatabase
import com.example.lastfm.artists.api.ArtistsApi
import com.example.lastfm.common.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideAlbumsApi(): AlbumsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .client(okHttpClient)
            .build()
            .create(AlbumsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideArtistsApi(): ArtistsApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .client(okHttpClient)
            .build()
            .create(ArtistsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAlbumsDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(context, AlbumsDatabase::class.java, Constants.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideAlbumDao(
        database: AlbumsDatabase,
    ) = database.albumDao()

//    @Singleton
//    @Provides
//    fun provideTopAlbumsApi(): TopAa {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("https://ws.audioscrobbler.com/2.0/")
//            .client(okHttpClient)
//            .build()
//            .create(ArtistsApi::class.java)
//    }
}
