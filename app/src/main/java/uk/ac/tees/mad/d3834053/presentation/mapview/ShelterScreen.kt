package uk.ac.tees.mad.d3834053.presentation.mapview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import uk.ac.tees.mad.d3834053.BottomNavBar
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.domain.shelters

object ShelterDestination : NavigationDestination {
    override val route = "shelter"
}

@Composable
fun ShelterScreen(
    navController: NavHostController
) {

    val shelterViewModel: ShelterViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        shelterViewModel.addSheltersToFirestore(shelters)
    }
    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(text = "Shelter")
        }
    }
}