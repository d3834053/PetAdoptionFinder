package uk.ac.tees.mad.d3834053.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoritePetEntity(
    @PrimaryKey
    val petId: String
)