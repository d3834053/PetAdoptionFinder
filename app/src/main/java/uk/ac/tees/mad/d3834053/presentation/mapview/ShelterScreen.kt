package uk.ac.tees.mad.d3834053.presentation.mapview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import uk.ac.tees.mad.d3834053.BottomNavBar
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.domain.Shelter
import uk.ac.tees.mad.d3834053.ui.theme.primaryYellow

object ShelterDestination : NavigationDestination {
    override val route = "shelter"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelterScreen(
    navController: NavHostController,
    viewModel: ShelterViewModel = hiltViewModel()
) {

    val shelters by viewModel.shelters.collectAsState()

    LaunchedEffect(true) {
        viewModel.getShelters()
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Shelters nearby",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White.copy(0.4f)),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(10f)
            )
            MapScreen(shelters = shelters)
        }
    }
}

@Composable
fun MapScreen(shelters: List<Shelter>) {
    val ukLocation = LatLng(53.4808, -2.2426) // Default location
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(ukLocation, 8f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        shelters.forEach { shelter ->
            Marker(
                state = MarkerState(
                    position = LatLng(
                        shelter.geopoint.latitude,
                        shelter.geopoint.longitude
                    )
                ),
                title = shelter.name,
                snippet = shelter.description
            )
        }
    }
}