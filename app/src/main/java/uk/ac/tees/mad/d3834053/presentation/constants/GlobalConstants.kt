package uk.ac.tees.mad.d3834053.presentation.constants

import android.annotation.SuppressLint
import android.content.Context
import com.google.firebase.auth.FirebaseUser
import uk.ac.tees.mad.d3834053.presentation.common.User

class GlobalConstants {
    companion object {

        var user: FirebaseUser? = null
            get() = field
            set(value) {
                field = value
            }

        var email_user: User? = null
            get() = field
            set(value) {
                field = value
            }

        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            get() = field
            set(value) {
                field = value
            }
    }
}