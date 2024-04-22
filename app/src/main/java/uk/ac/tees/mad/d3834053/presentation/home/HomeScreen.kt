package uk.ac.tees.mad.d3834053.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.constants.Categories
import uk.ac.tees.mad.d3834053.presentation.constants.Items
import uk.ac.tees.mad.d3834053.presentation.constants.itemsList

object HomeDestination : NavigationDestination {
    override val route = "home"
}

@Composable
fun HomeScreen(
    onNavigateToLogin: () -> Unit,
    onItemClick: () -> Unit
) {
    val selectedItemIndex = remember {
        mutableIntStateOf(0)
    }
    val selectedCategory: MutableState<String> = remember {
        mutableStateOf("Cats")
    }

    val categories = listOf(
        Categories(R.drawable.cat, "Cats"),
        Categories(R.drawable.dog, "Dogs"),
        Categories(R.drawable.bird, "Birds"),
    )

//FFA2A2A2
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Discover",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.size(320.dp, 40.dp)
                )
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(30.dp))
                    .background(
                        Color(
                            0xFFCCCCCC
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Search Pets", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.size(20.dp))
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Box")
                }
            }

            Spacer(modifier = Modifier.size(30.dp))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(categories) { index, item ->
                    CategoryCard(modifier = Modifier
                        .size(80.dp, 120.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (selectedItemIndex.intValue == index) Color(0xFFFFAE00)
                            else Color(0xFFBBBBBB)
                        )
                        .clickable {
                            selectedItemIndex.intValue = index
                            selectedCategory.value = item.title
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp), item = item)
                }
            }

            Spacer(modifier = Modifier.size(30.dp))

            LazyColumn {
                itemsIndexed(itemsList) { _, item ->
                    if (item.category == selectedCategory.value) {
                        PetsCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(10.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .clickable {
                                    onItemClick()
                                }, items = item
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun CategoryCard(modifier: Modifier, item: Categories) {
    return Box(
        modifier = modifier.clip(RoundedCornerShape(30.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = item.iconRes), contentDescription = item.title)
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                text = item.title, style = MaterialTheme.typography.bodyMedium, color = Color.Black
            )
        }
    }
}

@Composable
fun PetsCard(modifier: Modifier, items: Items) {
    val favouritePet = remember {
        mutableStateOf(false)
    }
    return Box(
        modifier = modifier.paint(
            painterResource(id = items.imageRes), contentScale = ContentScale.FillBounds
        )
    ) {
        IconButton(
            onClick = {
                favouritePet.value = !favouritePet.value
            },
            modifier = Modifier.align(
                Alignment.TopEnd
            ),
        ) {
            Icon(
                imageVector = if (favouritePet.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "",
                tint = if (favouritePet.value) Color.Red else Color.Black
            )
        }
        Text(
            text = items.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFAE00),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(
                    Alignment.BottomStart
                )
                .padding(12.dp)
        )
    }
}

/*
Button(
            onClick = {
                    Firebase.auth.signOut()
                    GlobalConstants.user = null
                    onNavigateToLogin()
                }
        ) {
            Text(text = "Home")

        }
 */