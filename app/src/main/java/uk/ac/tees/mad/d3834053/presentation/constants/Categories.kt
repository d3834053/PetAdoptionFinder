package uk.ac.tees.mad.d3834053.presentation.constants

data class Categories(
    var iconRes: Int,
    var title: String
)

data class Items(
    var id: Int,
    var name: String,
    var category: String,
    var imageRes: Int,
    var sex: String = "Male",
    var age: Int = 2,
    var weight: Double = 2.5,
    var address: String = "East Grinstead",
    var dob: String = "10 Jan 2020",
    var description: String = "lorem ipsum dolor",
    var contactPerson: ContactPerson = ContactPerson(
        name = "Owner name",
        phone = "9836251478",
        email = "owner@gmail.com"
    )
) {
    data class ContactPerson(
        val name: String,
        val phone: String,
        val email: String
    )
}