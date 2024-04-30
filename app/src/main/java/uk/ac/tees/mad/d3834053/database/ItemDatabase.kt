package uk.ac.tees.mad.d3834053.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoritePetEntity::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun getDao(): ItemDao
}