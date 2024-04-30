package uk.ac.tees.mad.d3834053.presentation.auth.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import uk.ac.tees.mad.d3834053.presentation.constants.GlobalConstants

class AuthRepository {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    suspend fun createUser(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val userMap = mapOf(
            "email" to email
        )
        try {
            Firebase.auth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        GlobalConstants.user = authResult.result.user
                        Firebase.firestore.collection("users")
                            .document(Firebase.auth.currentUser?.uid!!)
                            .set(userMap)
                            .addOnSuccessListener {
                            }
                        onComplete.invoke(true)
                    } else {
                        onComplete.invoke(false)
                    }
                }.await()
        } catch (e: Exception) {
            onComplete.invoke(false)
            e.printStackTrace()
        }
    }

    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {

            Firebase.auth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        GlobalConstants.user = it.result.user
                        onComplete.invoke(true)
                    } else {
                        onComplete.invoke(false)
                    }
                }.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}