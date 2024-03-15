package uk.ac.tees.mad.d3834053

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.d3834053.presentation.SplashDestination
import uk.ac.tees.mad.d3834053.presentation.SplashScreen
import uk.ac.tees.mad.d3834053.presentation.home.HomeDestination
import uk.ac.tees.mad.d3834053.presentation.home.HomeScreen

@Composable
fun PetAdoptionFinderNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SplashDestination.route) {
        composable(SplashDestination.route) {
            SplashScreen(
                onFinish = {
                    navController.navigate(HomeDestination.route)
                }
            )
        }
        composable(HomeDestination.route) {
            HomeScreen()
        }
    }
}

interface NavigationDestination {
    val route: String
}