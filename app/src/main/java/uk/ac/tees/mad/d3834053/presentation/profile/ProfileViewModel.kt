package uk.ac.tees.mad.d3834053.presentation.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.net.URL
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _getUserState = MutableStateFlow(UserDataState())
    val getUserState = _getUserState.asStateFlow()

    private val _updateUserState = MutableStateFlow(UserData())
    val updateUserState = _updateUserState.asStateFlow()

    private val _uiState = MutableStateFlow(UserData())
    val uiState = _uiState.asStateFlow()

    fun updateUserDataState(state: UserData) {
        _uiState.value = state
    }

    init {
        getUserInformation()
    }

    fun getUserInformation() = viewModelScope.launch {
        try {
            val userId = Firebase.auth.currentUser?.uid!!
            Firebase.firestore.collection("users").document(userId).get()
                .addOnSuccessListener { mySnapshot ->
                    if (mySnapshot.exists()) {
                        val data = mySnapshot.data
                        if (data != null) {
                            val userResponse = UserDataState(
                                userId = userId,
                                name = data["name"] as String? ?: "",
                                email = data["email"] as String? ?: "",
                                address = data["address"] as String? ?: "",
                                image = data["image"] as String? ?: ""
                            )

                            _getUserState.value = userResponse
                        } else {
                            println("No data found in Database")
                        }
                    } else {
                        println("No data found in Database")
                    }
                }.addOnFailureListener { e ->
                    e.printStackTrace()
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateProfile(context: Context) = viewModelScope.launch {

        val currentUserUid = Firebase.auth.currentUser?.uid
        val user = _uiState.value
        val storage = Firebase.storage.reference
        val imageRef = storage.child("user/${UUID.randomUUID()}")
        val uploadTask = user.image?.let {
            imageRef.putBytes(it)
        }

        uploadTask?.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val map = HashMap<String, Any>().apply {
                    put("name", user.name)
                    put("address", user.address)
                    put("image", uri.toString())
                }
                if (currentUserUid != null) {
                    Firebase.firestore.collection("users")
                        .document(currentUserUid)
                        .update(map)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                }
            }
        }?.addOnFailureListener { e ->
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }


    fun processSelectedImage(uri: Uri, context: Context) {
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media
                .getBitmap(context.contentResolver, uri)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        val imageByteArray = convertBitmapToByteArray(bitmap)
        _uiState.update {
            it.copy(image = imageByteArray)
        }
    }

    fun processCapturedImage(bitmap: Bitmap) {
        val imageByteArray = convertBitmapToByteArray(bitmap)
        _uiState.update {
            it.copy(image = imageByteArray)
        }
    }

}

fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    return outputStream.toByteArray()
}

fun getImageFromUrl(url: String): ByteArray? {
    try {
        val imageUrl = URL(url)
        val connection = imageUrl.openConnection()
        val inputStream = connection.getInputStream()
        val outputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var read = 0
        while (inputStream.read(buffer, 0, buffer.size).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }
        outputStream.flush()
        return outputStream.toByteArray()
    } catch (e: Exception) {
        Log.d("ImageManager", "Error: $e")
    }
    return null
}

data class UserResult(
    val data: UserDataState? = null,
    val isSuccessful: Boolean = false,
    val errorMessage: String? = null
)

data class UserUpdateResult(
    val data: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class UserDataState(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val image: String? = null
)

data class UserData(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val address: String = "",
    val image: ByteArray? = null
)
