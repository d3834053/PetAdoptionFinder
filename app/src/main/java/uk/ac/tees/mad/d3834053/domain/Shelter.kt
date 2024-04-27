package uk.ac.tees.mad.d3834053.domain

import com.google.firebase.firestore.GeoPoint


data class Shelter(
    val name: String,
    val description: String,
    val geopoint: GeoPoint,
    val address: String
)