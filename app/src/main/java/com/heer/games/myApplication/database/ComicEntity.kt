package com.heer.games.myApplication.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comics")
data class ComicEntity (
    @ColumnInfo(name = "id") @PrimaryKey val comic_id : Int,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "genre") val genre : String,
    @ColumnInfo(name = "author") val author : String,
    @ColumnInfo(name = "imgId") val imgId : Int
)