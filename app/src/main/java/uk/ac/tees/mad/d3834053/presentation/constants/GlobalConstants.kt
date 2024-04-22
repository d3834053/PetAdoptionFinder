package uk.ac.tees.mad.d3834053.presentation.constants

import android.annotation.SuppressLint
import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.common.User

class GlobalConstants {
    companion object {

        var user: FirebaseUser? = null
            get() = field
            set(value) { field = value }

        var email_user :User?= null
            get() = field
            set(value) {field= value}

        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            get() = field
            set(value) { field = value }
    }

}


val itemsList = listOf(
    Items(id = 1, name = "Abyssinian", category = "Cats", imageRes = R.drawable.abyssinian),
    Items(
        id = 2,
        name = "American Bulldog",
        category = "Dogs",
        imageRes = R.drawable.american_bulldog
    ),
    Items(
        id = 3,
        name = "American PitBull",
        category = "Dogs",
        imageRes = R.drawable.american_pit_bull_terrier
    ),
    Items(id = 4, name = "Basset Hound", category = "Dogs", imageRes = R.drawable.basset_hound),
    Items(id = 5, name = "Beagle", category = "Dogs", imageRes = R.drawable.beagle),
    Items(id = 6, name = "Bengal", category = "Cats", imageRes = R.drawable.bengal),
    Items(id = 7, name = "Birman", category = "Cats", imageRes = R.drawable.birman),
    Items(id = 8, name = "Bombay", category = "Cats", imageRes = R.drawable.bombay),
    Items(id = 9, name = "Boxer", category = "Dogs", imageRes = R.drawable.boxer),
    Items(
        id = 10,
        name = "British ShortHair",
        category = "Cats",
        imageRes = R.drawable.british_shorthair
    ),
    Items(id = 11, name = "Chihuahua", category = "Dogs", imageRes = R.drawable.chihuahua),
    Items(
        id = 13, name = "Egyptian Mau", category = "Cats", imageRes = R.drawable.egyptian_mau
    ),
    Items(
        id = 14,
        name = "Cocker Spaniel",
        category = "Dogs",
        imageRes = R.drawable.english_cocker_spaniel
    ),
    Items(
        id = 15,
        name = "English Setter",
        category = "Dogs",
        imageRes = R.drawable.english_setter
    ),
    Items(
        id = 16,
        name = "German ShotHair",
        category = "Dogs",
        imageRes = R.drawable.german_shorthaired
    ),
    Items(
        id = 17,
        name = "Great Pyrenees",
        category = "Dogs",
        imageRes = R.drawable.great_pyrenees
    ),
    Items(id = 18, name = "Maine Coon", category = "Cats", imageRes = R.drawable.maine_coon),


    )