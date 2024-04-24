package uk.ac.tees.mad.d3834053.domain

import com.google.firebase.firestore.GeoPoint


data class Shelter(
    val name: String,
    val description: String,
    val geopoint: GeoPoint,
    val address: String
)

val shelters = listOf(
    Shelter(
        name = "London Animal Shelter",
        description = "A shelter in London for rescued animals",
        geopoint = GeoPoint( 51.5074, -0.1278),
        address = "123 Main Street, London"
    ),
    Shelter(
        name = "Manchester Pet Rescue",
        description = "Rescuing and rehoming pets in Manchester",
        geopoint = GeoPoint( 53.4808, -2.2426),
        address = "456 Elm Avenue, Manchester"
    ),
    Shelter(
        name = "Birmingham Animal Haven",
        description = "Providing care for animals in Birmingham",
        geopoint = GeoPoint( 52.4862, -1.8904),
        address = "789 Oak Road, Birmingham"
    ),
    Shelter(
        name = "Edinburgh Pet Sanctuary",
        description = "Helping pets find forever homes in Edinburgh",
        geopoint = GeoPoint( 55.9533, -3.1883),
        address = "101 Maple Street, Edinburgh"
    ),
    Shelter(
        name = "Glasgow Rescue Center",
        description = "Rescuing and rehabilitating animals in Glasgow",
        geopoint = GeoPoint( 55.8642, -4.2518),
        address = "222 Pine Avenue, Glasgow"
    ),
    Shelter(
        name = "Liverpool Animal Care",
        description = "Caring for animals in Liverpool",
        geopoint = GeoPoint( 53.4084, -2.9916),
        address = "789 Willow Lane, Liverpool"
    ),
    Shelter(
        name = "Leeds Pet Haven",
        description = "Providing shelter and love to pets in Leeds",
        geopoint = GeoPoint( 53.8008, -1.5491),
        address = "567 Oak Street, Leeds"
    ),
    Shelter(
        name = "Cardiff Animal Rescue",
        description = "Rescuing and rehoming animals in Cardiff",
        geopoint = GeoPoint( 51.4816, -3.1791),
        address = "987 Elm Road, Cardiff"
    ),
    Shelter(
        name = "Newcastle Upon Tyne Pet Shelter",
        description = "Sheltering pets in Newcastle Upon Tyne",
        geopoint = GeoPoint( 54.9784, -1.6174),
        address = "345 Birch Lane, Newcastle Upon Tyne"
    ),
    Shelter(
        name = "Sheffield Animal Refuge",
        description = "Providing refuge and care for animals in Sheffield",
        geopoint = GeoPoint( 53.3811, -1.4701),
        address = "654 Maple Avenue, Sheffield"
    )
)
