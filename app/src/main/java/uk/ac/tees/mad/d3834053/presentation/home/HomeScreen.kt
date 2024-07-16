package uk.ac.tees.mad.d3834053.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uk.ac.tees.mad.d3834053.BottomNavBar
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.constants.Categories
import uk.ac.tees.mad.d3834053.ui.theme.primaryYellow

object HomeDestination : NavigationDestination {
    override val route = "home"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onItemClick: (String) -> Unit, navController: NavHostController
) {
    val selectedItemIndex = remember {
        mutableIntStateOf(0)
    }
    val selectedCategory: MutableState<String> = remember {
        mutableStateOf("Cat")
    }

    val homeViewModel: HomeViewModel = hiltViewModel()
    val petList = homeViewModel.petList
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        homeViewModel.getAllPetsFromFirestore()
    }

    val categories = listOf(
        Categories(R.drawable.cat, "Cat"),
        Categories(R.drawable.dog, "Dog"),
        Categories(R.drawable.bird, "Bird"),
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Discover", style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(FavoriteDestination.route) }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = primaryYellow
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = primaryYellow
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column {

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(categories) { index, item ->
                        CategoryCard(modifier = Modifier
                            .height(120.dp)
                            .aspectRatio(1f)
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

                LazyColumn {
                    items(petList) { item ->
                        if (item.category == selectedCategory.value) {
                            PetsCard(
                                onClick = {
                                    onItemClick(item.id)
                                },
                                items = item,
                                onFavoriteClick = {

                                    homeViewModel.addPetToFavorite(item, context)
                                }
                            )
                        }
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
            Image(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.title,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
            Text(
                text = item.title, style = MaterialTheme.typography.titleMedium, color = Color.Black
            )
        }
    }
}

@Composable
fun PetsCard(
    onClick: () -> Unit, items: PetItem, onFavoriteClick: () -> Unit, favouritePet: Boolean = false
) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)
        .padding(10.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable {
            onClick()
        }) {
        if (items.image.isEmpty()) {
            Image(
                imageVector = Icons.Default.Image,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).crossfade(true).data(items.image)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier
                .padding(6.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(
                    Alignment.BottomEnd
                )
                .background(Color.White.copy(0.9f)),
        ) {
            Icon(
                imageVector = if (favouritePet) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = "",
                tint = if (favouritePet) Color.Red else Color.Black
            )
        }
        Text(
            text = items.name,
            fontSize = 24.sp,
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