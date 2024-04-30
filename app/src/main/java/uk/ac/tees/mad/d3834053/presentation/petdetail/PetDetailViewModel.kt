package uk.ac.tees.mad.d3834053.presentation.petdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.ac.tees.mad.d3834053.presentation.home.PetItem
import javax.inject.Inject

@HiltViewModel
class PetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val petId: String = checkNotNull(savedStateHandle[PetDetailDestination.petIdArg])

    private val _pet = MutableStateFlow(PetItem())
    val pet = _pet.asStateFlow()

    init {
        getPetByIdFromFirestore(petId)
    }

    private fun getPetByIdFromFirestore(petId: String) {
        val db = Firebase.firestore
        db.collection("pets").document(petId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val pet = document.toObject<PetItem>()
                    if (pet != null) {
                        _pet.value = pet
                    }
                    if (pet != null) {
                        println("Pet Name: ${pet.name}, Category: ${pet.category}")
                    } else {
                        println("No pet found with ID: $petId")
                    }
                } else {
                    println("No pet found with ID: $petId")
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting pet by ID: $exception")
            }
    }
}