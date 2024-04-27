package uk.ac.tees.mad.d3834053.presentation.mapview

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.ac.tees.mad.d3834053.domain.Shelter
import javax.inject.Inject

@HiltViewModel
class ShelterViewModel @Inject constructor() : ViewModel() {

    private val _shelters = MutableStateFlow<List<Shelter>>(emptyList())
    val shelters: StateFlow<List<Shelter>> = _shelters

    fun getShelters() {
        getSheltersFromFirestore { shelters ->
            _shelters.value = shelters
        }
    }
}

fun getSheltersFromFirestore(onResult: (List<Shelter>) -> Unit) {
    val db = Firebase.firestore
    db.collection("shelters")
        .get()
        .addOnSuccessListener { result ->
            val shelters = mutableListOf<Shelter>()
            for (document in result) {
                val name = document.getString("name") ?: ""
                val description = document.getString("description") ?: ""
                val address = document.getString("address") ?: ""
                val geopoint = document.get("geopoint") as GeoPoint

                val shelter = Shelter(name, description, geopoint, address)
                shelters.add(shelter)
            }
            onResult(shelters)
        }
        .addOnFailureListener { exception ->
            Log.w("Firestore", "Error getting documents: ", exception)
        }
}