package com.heer.games.myApplication.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ComicEntity::class],version = 1)
abstract class ComicDB: RoomDatabase() {
    abstract fun comicDao() : ComicDao
}