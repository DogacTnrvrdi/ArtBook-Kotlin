package com.dogactnrvrdi.artbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dogactnrvrdi.artbook.model.Art

@Database(entities = [Art::class], version = 1)
abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao() : ArtDAO
}