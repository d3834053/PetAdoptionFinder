package uk.ac.tees.mad.d3834053.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(favoritePetEntity: FavoritePetEntity)

    @Query("select * from FavoritePetEntity")
    fun getAllFavorite(): Flow<List<FavoritePetEntity>>

    @Delete
    suspend fun deleteFromFavorite(favoritePetEntity: FavoritePetEntity)

}