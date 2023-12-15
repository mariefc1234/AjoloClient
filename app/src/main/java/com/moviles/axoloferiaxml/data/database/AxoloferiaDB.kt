package com.moviles.axoloferiaxml.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [StallFavorite::class],
    version = 1
)


abstract class AxoloferiaDB : RoomDatabase() {
    abstract fun stallFavoriteDAO(): StallFavoriteDAO
}