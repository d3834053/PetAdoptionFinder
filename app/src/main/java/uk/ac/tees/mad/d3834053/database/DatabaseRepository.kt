package uk.ac.tees.mad.d3834053.database

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.d3834053.presentation.home.PetItem

interface DatabaseRepository {

    suspend fun addToFavorite(item: PetItem)

    fun getAllFavorite(): Flow<List<FavoritePetEntity>>

    suspend fun deleteFromFavorite(item: PetItem)
}

class DatabaseRepositoryImpl(
    private val dao: ItemDao
) : DatabaseRepository {
    override suspend fun addToFavorite(item: PetItem) {
        dao.addToFavorite(item.toFavorite())
    }

    override fun getAllFavorite(): Flow<List<FavoritePetEntity>> = dao.getAllFavorite()

    override suspend fun deleteFromFavorite(item: PetItem) =
        dao.deleteFromFavorite(item.toFavorite())

}