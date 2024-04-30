package uk.ac.tees.mad.d3834053.presentation.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.d3834053.database.DatabaseRepository
import uk.ac.tees.mad.d3834053.database.FavoritePetEntity
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    var petList by mutableStateOf(emptyList<PetItem>())
        private set

    var allFavorites by mutableStateOf(listOf<String>())
        private set

    var favoriteList by mutableStateOf(listOf<PetItem>())
        private set

    fun getAllPetsFromFirestore() {
        val db = Firebase.firestore
        db.collection("pets")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val pet = document.toObject<PetItem>()
                    pet.id = document.id
                    petList = petList + pet
                    println("Pet Name: ${pet.name}, Category: ${pet.category}")
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting documents: $exception")
            }
    }

    fun loadAllFavorites() {
        viewModelScope.launch {
            databaseRepository.getAllFavorite().onEach {
                allFavorites = it.map { favorite ->
                    favorite.petId
                }
                getFavorites()
            }.launchIn(viewModelScope)
        }
    }

    private fun getFavorites() = viewModelScope.launch {
        try {
            val items = mutableListOf<PetItem>()
            for (key in allFavorites) {
                val document = Firebase.firestore.collection("pets").document(key).get().await()
                if (document.exists()) {
                    val pet = document.toObject<PetItem>()
                    if (pet != null) {
                        items.add(pet)
                    }
                }
            }
            favoriteList = items
        } catch (e: Exception) {
            Log.w("Error Fetching", "Error fetching documents", e)
        }
    }

    fun addPetToFavorite(pet: PetItem, context: Context) = viewModelScope.launch {
        databaseRepository.addToFavorite(pet)
    }.invokeOnCompletion {
        Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT)
            .show()
    }

    fun deleteFromFavorite(item: PetItem, context: Context) = viewModelScope.launch {
        databaseRepository.deleteFromFavorite(item)
        // After deletion, refresh the list of all favorites.
        loadAllFavorites()
    }.invokeOnCompletion { error ->
        if (error == null) {
            Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show()
        }
    }
}

data class PetItem(
    var id: String = "",
    val name: String = "",
    val category: String = "",
    val image: String = "",
    val sex: String = "",
    val age: Int = 0,
    val weight: Double = 0.0,
    val address: String = "",
    val dob: String = "",
    val description: String = "",
    val contactPerson: ContactPerson = ContactPerson()
) {
    data class ContactPerson(
        val name: String = "",
        val phone: String = "",
        val email: String = ""
    )

    fun toFavorite() = FavoritePetEntity(
        petId = id
    )
}