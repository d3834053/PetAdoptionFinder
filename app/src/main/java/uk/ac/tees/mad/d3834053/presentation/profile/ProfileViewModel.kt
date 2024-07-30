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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.net.URL
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _userStateFlow = MutableStateFlow(UserProfileState())
    val userStateFlow: StateFlow<UserProfileState> = _userStateFlow

    private val _userUpdateFlow = MutableStateFlow(UserProfile())
    val userUpdateFlow: StateFlow<UserProfile> = _userUpdateFlow

    private val _profileUIState = MutableStateFlow(UserProfile())
    val profileUIState: StateFlow<UserProfile> = _profileUIState

    fun modifyUserProfileData(newState: UserProfile) {
        _profileUIState.value = newState
    }

    init {
        fetchUserDetails()
    }

    fun fetchUserDetails() = viewModelScope.launch {
        try {
            val userIdentifier = Firebase.auth.currentUser?.uid.orEmpty()
            Firebase.firestore.collection("users").document(userIdentifier).get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        documentSnapshot.data?.let { userData ->
                            val userProfile = UserProfileState(
                                userId = userIdentifier,
                                name = userData["name"] as? String ?: "Unknown name",
                                email = userData["email"] as? String ?: "",
                                address = userData["address"] as? String ?: "",
                                image = userData["image"] as? String ?: ""
                            )
                            _userStateFlow.value = userProfile
                        } ?: kotlin.run { println("Database record is empty") }
                    } else {
                        println("No record found in Database")
                    }
                }.addOnFailureListener { exception ->
                    exception.printStackTrace()
                }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun submitProfileUpdate(context: Context) = viewModelScope.launch {
        val currentUserId = Firebase.auth.currentUser?.uid
        val userProfile = _profileUIState.value
        val storagePath = Firebase.storage.reference
        val userProfileImageRef = storagePath.child("user_images/${UUID.randomUUID()}")
        val imageUploadTask = userProfile.image?.let {
            userProfileImageRef.putBytes(it)
        }

        imageUploadTask?.addOnSuccessListener {
            userProfileImageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                val updateMap = mapOf(
                    "name" to userProfile.name,
                    "address" to userProfile.address,
                    "image" to imageUrl.toString(),
                    "email" to Firebase.auth.currentUser?.email
                )
                currentUserId?.let { userId ->
                    Firebase.firestore.collection("users")
                        .document(userId)
                        .set(updateMap)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Profile Updated Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                fetchUserDetails()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show()
                        }
                } ?: Toast.makeText(context, "User is not signed in", Toast.LENGTH_SHORT).show()
            }
        }?.addOnFailureListener {
            Toast.makeText(context, "Image Upload Failed", Toast.LENGTH_SHORT).show()
            it.printStackTrace()
        }
    }

    fun handleImageSelection(uri: Uri, context: Context) {
        val imageBitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        val byteArrayImage = bitmapToByteArray(imageBitmap)
        _profileUIState.update { currentState ->
            currentState.copy(image = byteArrayImage)
        }
    }

    fun handleImageCapture(bitmap: Bitmap) {
        val byteArrayImage = bitmapToByteArray(bitmap)
        _profileUIState.update { currentState ->
            currentState.copy(image = byteArrayImage)
        }
    }

}

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    ByteArrayOutputStream().apply {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
        return toByteArray()
    }
}

fun fetchImageFromURL(urlString: String): ByteArray? {
    return try {
        val url = URL(urlString)
        url.openStream().use { inputStream ->
            val outputStream = ByteArrayOutputStream()
            inputStream.copyTo(outputStream)
            outputStream.toByteArray()
        }
    } catch (exception: Exception) {
        Log.e("ImageFetchError", "Error fetching image: $exception")
        null
    }
}

data class UserProfileState(
    val userId: String = "",
    val name: String = "Guest",
    val email: String = "guest@gmail.com",
    val address: String = "",
    val image: String? = null
)

data class UserProfile(
    val userId: String = "",
    val name: String =  "Guest",
    val email: String = "guest@gmail.com",
    val address: String = "",
    val image: ByteArray? = null
)