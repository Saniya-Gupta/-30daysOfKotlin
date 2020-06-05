package com.heer.games.myApplication.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ComicDao {
    @Insert
    fun insertComic(comicEntity: ComicEntity)

    @Delete
    fun removeComic(comicEntity: ComicEntity)

    @Query("Select * from Comics")
    fun getAllComics() : List<ComicEntity>

    @Query("Select * from Comics where id = :comicId")
    fun getComicById(comicId : String) : ComicEntity
}