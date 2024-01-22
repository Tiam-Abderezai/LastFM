package com.example.lastfm.albums.local


import com.example.lastfm.common.utils.logger.BaseLogger
import com.example.lastfm.common.utils.logger.FactoryLogger
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private val logger: BaseLogger = FactoryLogger.getLoggerKClass(AlbumsDBImpl::class)
class AlbumsDBImpl @Inject constructor(
    private val albumDao: AlbumDao,
) : AlbumsDBRepository {

    override fun queryAlbumEntities(): Flow<List<AlbumEntity>> {
        logger.debug("albumDao.observeAllAlbums(): ${albumDao.observeAllAlbums()}")
        return albumDao.observeAllAlbums()
    }

    override fun doesAlbumExist(mBid: String?): Boolean {
        logger.debug("albumDao.doesAlbumExist($mBid): ${albumDao.doesAlbumExist(mBid ?: "null")})")
        return albumDao.doesAlbumExist(mBid ?: "null")
    }

    override fun queryAlbumEntity(mBid: String?): AlbumEntity {
        logger.debug("albumDao.queryAlbumEntity($mBid): ${albumDao.queryAlbum(mBid ?: "null")})")
        return albumDao.queryAlbum(mBid ?: "null")
    }

    override suspend fun insertAlbum(album: AlbumEntity) {
        albumDao.insertAlbum(album)
    }

    override suspend fun deleteAlbum(album: AlbumEntity) {
        albumDao.deleteAlbum(album)
    }
}