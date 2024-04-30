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

//
//val itemsList = listOf(
//
//    Items(
//        id = "1",
//        name = "Buddy",
//        category = "Dog",
//        image = "https://cdn.britannica.com/82/232782-050-8062ACFA/Black-labrador-retriever-dog.jpg",
//        sex = "Male",
//        age = 3,
//        weight = 20.0,
//        address = "123 Pet Street, Petville",
//        dob = "01 Mar 2020",
//        description = "A friendly and energetic Labrador.",
//        contactPerson = Items.ContactPerson(
//            name = "John Doe",
//            phone = "1234567890",
//            email = "johndoe@example.com"
//        )
//    ),
//    Items(
//        id = "2",
//        name = "Whiskers",
//        category = "Cat",
//        image = "https://cdn-aahmh.nitrocdn.com/mwIJloVUffDtKiCgRcivopdgojcJrVwT/assets/images/optimized/rev-463a7fd/www.cozycatfurniture.com/image/siamese-cat-cover.jpg",
//        sex = "Female",
//        age = 2,
//        weight = 4.5,
//        address = "456 Meow Lane, Catstown",
//        dob = "15 May 2021",
//        description = "A curious and playful Siamese cat.",
//        contactPerson = Items.ContactPerson(
//            name = "Jane Doe",
//            phone = "0987654321",
//            email = "janedoe@example.com"
//        )
//    ),
//    Items(
//        id = "3",
//        name = "Tweety",
//        category = "Bird",
//        image = "https://cdn.download.ams.birds.cornell.edu/api/v1/asset/147508641/900",
//        sex = "Female",
//        age = 1,
//        weight = 0.1,
//        address = "789 Chirp Road, Birdsville",
//        dob = "07 Jul 2022",
//        description = "A cheerful and talkative Parakeet.",
//        contactPerson = Items.ContactPerson(
//            name = "Alice Smith",
//            phone = "1122334455",
//            email = "alicesmith@example.com"
//        )
//    ),
//    Items(
//        id = "4",
//        name = "Shadow",
//        category = "Dog",
//        image = "https://cdn.britannica.com/79/232779-050-6B0411D7/German-Shepherd-dog-Alsatian.jpg",
//        sex = "Male",
//        age = 5,
//        weight = 25.0,
//        address = "101 Bark Avenue, Dogtown",
//        dob = "22 Oct 2018",
//        description = "A loyal and protective German Shepherd.",
//        contactPerson = Items.ContactPerson(
//            name = "Ella Brown",
//            phone = "3344556677",
//            email = "ellabrown@example.com"
//        )
//    ),
//    Items(
//        id = "5",
//        name = "Mittens",
//        category = "Cat",
//        image = "https://cdn.shopify.com/s/files/1/1199/8502/files/persian-doll-face.jpg",
//        sex = "Female",
//        age = 3,
//        weight = 3.2,
//        address = "202 Purr Street, Catville",
//        dob = "09 Jan 2020",
//        description = "A shy but affectionate Persian cat.",
//        contactPerson = Items.ContactPerson(
//            name = "Tom Green",
//            phone = "2233445566",
//            email = "tomgreen@example.com"
//        )
//    ),
//    Items(
//        id = "6",
//        name = "Coco",
//        category = "Bird",
//        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1htTHQR2bSik1ecMjc8_c_4Sfxv1KhqSd4PW6Tpe97w&s",
//        sex = "Male",
//        age = 2,
//        weight = 0.5,
//        address = "303 Tweet Lane, Birdtown",
//        dob = "15 Mar 2021",
//        description = "A vibrant and intelligent African Grey parrot.",
//        contactPerson = Items.ContactPerson(
//            name = "Sarah White",
//            phone = "4455667788",
//            email = "sarahwhite@example.com"
//        )
//    ),
//    Items(
//        id = "7",
//        name = "Bella",
//        category = "Dog",
//        image = "https://cdn.britannica.com/16/234216-050-C66F8665/beagle-hound-dog.jpg",
//        sex = "Female",
//        age = 4,
//        weight = 18.0,
//        address = "404 Woof Way, Dogville",
//        dob = "30 Jul 2019",
//        description = "A playful and friendly Beagle.",
//        contactPerson = Items.ContactPerson(
//            name = "Mike Johnson",
//            phone = "5566778899",
//            email = "mikejohnson@example.com"
//        )
//    ),
//    Items(
//        id = "8",
//        name = "Simba",
//        category = "Cat",
//        image = "https://www.catster.com/wp-content/uploads/2023/12/bengal-cat-sitting-on-the-floor_Eric-Isselee_Shutterstock.jpg",
//        sex = "Male",
//        age = 1,
//        weight = 5.0,
//        address = "505 Claw Court, Catstown",
//        dob = "05 May 2022",
//        description = "An adventurous and fearless Bengal cat.",
//        contactPerson = Items.ContactPerson(
//            name = "Linda Martin",
//            phone = "6677889900",
//            email = "lindamartin@example.com"
//        )
//    ),
//    Items(
//        id = "9",
//        name = "Sky",
//        category = "Bird",
//        image = "https://cdn.britannica.com/33/226533-050-404C15AF/Canary-on-pear-branch.jpg",
//        sex = "Female",
//        age = 3,
//        weight = 0.2,
//        address = "606 Flap Street, Birdville",
//        dob = "21 Dec 2020",
//        description = "A gentle and melodious Canary.",
//        contactPerson = Items.ContactPerson(
//            name = "Chris Lee",
//            phone = "7788990011",
//            email = "chrislee@example.com"
//        )
//    ),
//    Items(
//        id = "10",
//        name = "Rex",
//        category = "Dog",
//        image = "https://cdn.britannica.com/69/234469-050-B883797B/Rottweiler-dog.jpg",
//        sex = "Male",
//        age = 6,
//        weight = 30.0,
//        address = "707 Bark Park, Dogtown",
//        dob = "10 Feb 2017",
//        description = "A brave and strong Rottweiler.",
//        contactPerson = Items.ContactPerson(
//            name = "Nora Fields",
//            phone = "8899001122",
//            email = "norafields@example.com"
//        )
//    ),
//    Items(
//        id = "11",
//        name = "Luna",
//        category = "Cat",
//        image = "https://images2.minutemediacdn.com/image/upload/c_crop,w_5616,h_3159,x_0,y_274/c_fill,w_720,ar_16:9,f_auto,q_auto,g_auto/images/voltaxMediaLibrary/mmsport/mentalfloss/01g8gy1489544hpt087f.jpg",
//        sex = "Female",
//        age = 4,
//        weight = 6.0,
//        address = "808 Meow Manor, Catville",
//        dob = "24 Aug 2019",
//        description = "A mysterious and elegant Russian Blue cat.",
//        contactPerson = Items.ContactPerson(
//            name = "Derek Stone",
//            phone = "9900112233",
//            email = "derekstone@example.com"
//        )
//    ),
//    Items(
//        id = "12",
//        name = "Pepper",
//        category = "Bird",
//        image = "https://assets.petco.com/petco/image/upload/f_auto,q_auto/lovebird-care-sheet-hero",
//        sex = "Male",
//        age = 2,
//        weight = 0.3,
//        address = "909 Chirp Circle, Birdtown",
//        dob = "05 Jun 2021",
//        description = "A playful and colorful Lovebird.",
//        contactPerson = Items.ContactPerson(
//            name = "Rachel Kim",
//            phone = "1011121314",
//            email = "rachelkim@example.com"
//        )
//    ),
//    Items(
//        id = "13",
//        name = "Daisy",
//        category = "Dog",
//        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6U2jDFmsVbZadc5_ajUSO_lKOzSVRTXq22VyXDzQl5A&s",
//        sex = "Female",
//        age = 3,
//        weight = 22.0,
//        address = "1010 Woof Walk, Dogville",
//        dob = "15 Mar 2020",
//        description = "A gentle and caring Golden Retriever.",
//        contactPerson = Items.ContactPerson(
//            name = "Sam Patel",
//            phone = "1516171819",
//            email = "sampatel@example.com"
//        )
//    ),
//    Items(
//        id = "14",
//        name = "Oliver",
//        category = "Cat",
//        image = "https://www.zigly.com/media/mageplaza/blog/post/m/a/maine_coon_cat.png",
//        sex = "Male",
//        age = 5,
//        weight = 7.0,
//        address = "1111 Purr Path, Catstown",
//        dob = "09 Sep 2018",
//        description = "A laid-back and friendly Maine Coon.",
//        contactPerson = Items.ContactPerson(
//            name = "Emily Wright",
//            phone = "2021222324",
//            email = "emilywright@example.com"
//        )
//    ),
//    Items(
//        id = "15",
//        name = "Kiwi",
//        category = "Bird",
//        image = "https://cdn.download.ams.birds.cornell.edu/api/v1/asset/123378071/1800",
//        sex = "Female",
//        age = 1,
//        weight = 0.4,
//        address = "1212 Tweet Trail, Birdville",
//        dob = "18 Dec 2022",
//        description = "A small but mighty Budgerigar.",
//        contactPerson = Items.ContactPerson(
//            name = "Lucas Moore",
//            phone = "2526272829",
//            email = "lucasmoore@example.com"
//        )
//    ),
//    Items(
//        id = "16",
//        name = "Max",
//        category = "Dog",
//        image = "https://cdn.britannica.com/46/233846-050-8D30A43B/Boxer-dog.jpg",
//        sex = "Male",
//        age = 4,
//        weight = 28.0,
//        address = "1313 Bark Boulevard, Dogtown",
//        dob = "22 May 2019",
//        description = "A friendly and energetic Boxer.",
//        contactPerson = Items.ContactPerson(
//            name = "Olivia Lee",
//            phone = "3031323334",
//            email = "olivialee@example.com"
//        )
//    ),
//    Items(
//        id = "17",
//        name = "Socks",
//        category = "Cat",
//        image = "https://www.thesprucepets.com/thmb/8o5e2mkJcAr4kODvsllTf2xiioo=/4778x0/filters:no_upscale():strip_icc()/GettyImages-925319984-36b97d913d934d229d8b0d528a7da64e.jpg",
//        sex = "Female",
//        age = 2,
//        weight = 4.0,
//        address = "1414 Meow Mile, Catville",
//        dob = "30 Oct 2021",
//        description = "A playful and mischievous American Shorthair.",
//        contactPerson = Items.ContactPerson(
//            name = "Ethan Brown",
//            phone = "3536373839",
//            email = "ethanbrown@example.com"
//        )
//    ),
//    Items(
//        id = "18",
//        name = "Finn",
//        category = "Bird",
//        image = "https://www.thesprucepets.com/thmb/yMixSt-B9KOLTiNy12J6OGgHVi0=/3000x0/filters:no_upscale():strip_icc()/cockatiels-as-pets-1236728-hero-78cbdaa2b96343a7bd3c11d4117fb931.jpg",
//        sex = "Male",
//        age = 3,
//        weight = 0.6,
//        address = "1515 Chirp Chateau, Birdtown",
//        dob = "07 Jul 2020",
//        description = "A smart and talkative Cockatiel.",
//        contactPerson = Items.ContactPerson(
//            name = "Sophia Johnson",
//            phone = "4041424344",
//            email = "sophiajohnson@example.com"
//        )
//    ),
//    Items(
//        id = "19",
//        name = "Buddy",
//        category = "Dog",
//        image = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Dobermann_handling.jpg/800px-Dobermann_handling.jpg",
//        sex = "Male",
//        age = 7,
//        weight = 32.0,
//        address = "1616 Woof Way, Dogville",
//        dob = "03 Mar 2016",
//        description = "A loyal and protective Doberman.",
//        contactPerson = Items.ContactPerson(
//            name = "Mia Davis",
//            phone = "4546474849",
//            email = "miadavis@example.com"
//        )
//    ),
//    Items(
//        id = "20",
//        name = "Zoe",
//        category = "Cat",
//        image = "https://www.catster.com/wp-content/uploads/2023/11/Brown-tabby-cat-that-curls-up-outdoors_viper-zero_Shutterstock.jpg",
//        sex = "Female",
//        age = 3,
//        weight = 5.5,
//        address = "1717 Purr Place, Catstown",
//        dob = "11 Nov 2020",
//        description = "An energetic and playful Tabby.",
//        contactPerson = Items.ContactPerson(
//            name = "Noah Wilson",
//            phone = "5051525354",
//            email = "noahwilson@example.com"
//        )
//    )
//
//)