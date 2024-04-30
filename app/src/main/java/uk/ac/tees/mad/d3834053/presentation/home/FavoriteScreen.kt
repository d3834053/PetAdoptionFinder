package uk.ac.tees.mad.d3834053.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.ui.theme.primaryYellow

object FavoriteDestination : NavigationDestination {
    override val route = "favorite"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    onBack: () -> Unit,
    onItemClick: (String) -> Unit
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val petList = homeViewModel.favoriteList
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        homeViewModel.loadAllFavorites()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Favorite Pets") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryYellow),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                if (petList.isEmpty()) {
                    item {
                        Text(text = "No favorite pets", modifier = Modifier.padding(16.dp))
                    }
                } else {
                    items(
                        items = petList
                    ) { item ->
                        PetsCard(
                            onClick = {
                                onItemClick(item.id)
                            }, items = item,
                            onFavoriteClick = {
                                homeViewModel.deleteFromFavorite(item, context)
                            },
                            favouritePet = true
                        )

                    }
                }
            }
        }
    }
}