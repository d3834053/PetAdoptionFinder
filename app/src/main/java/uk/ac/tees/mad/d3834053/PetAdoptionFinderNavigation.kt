package uk.ac.tees.mad.d3834053

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.d3834053.presentation.SplashDestination
import uk.ac.tees.mad.d3834053.presentation.SplashScreen
import uk.ac.tees.mad.d3834053.presentation.auth.login.LoginDestination
import uk.ac.tees.mad.d3834053.presentation.auth.login.LoginScreen
import uk.ac.tees.mad.d3834053.presentation.auth.registration.RegistrationDestination
import uk.ac.tees.mad.d3834053.presentation.auth.registration.RegistrationScreen
import uk.ac.tees.mad.d3834053.presentation.home.HomeDestination
import uk.ac.tees.mad.d3834053.presentation.home.HomeScreen
import uk.ac.tees.mad.d3834053.presentation.mapview.ShelterDestination
import uk.ac.tees.mad.d3834053.presentation.mapview.ShelterScreen
import uk.ac.tees.mad.d3834053.presentation.onboarding.OnboardScreen
import uk.ac.tees.mad.d3834053.presentation.onboarding.OnboardingDestination
import uk.ac.tees.mad.d3834053.presentation.petdetail.PetDetailDestination
import uk.ac.tees.mad.d3834053.presentation.petdetail.PetDetailScreen
import uk.ac.tees.mad.d3834053.presentation.profile.EditProfile
import uk.ac.tees.mad.d3834053.presentation.profile.EditProfileDestination
import uk.ac.tees.mad.d3834053.presentation.profile.ProfileDestination
import uk.ac.tees.mad.d3834053.presentation.profile.ProfileScreen
import uk.ac.tees.mad.d3834053.ui.theme.primaryYellow

private fun isOnboardingFinished(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("isFinished", false)
}

@Composable
fun PetAdoptionFinderNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current


    NavHost(navController = navController, startDestination = SplashDestination.route) {
        composable(SplashDestination.route) {
            SplashScreen(
                onFinish = {

                    val destinationScreen =
                        if (isOnboardingFinished(context)) LoginDestination.route else OnboardingDestination.route
                    navController.popBackStack()
                    navController.navigate(destinationScreen)
                }
            )
        }
        composable(OnboardingDestination.route) {
            OnboardScreen { navController.navigate(route = LoginDestination.route) }
        }
        composable(HomeDestination.route) {
            HomeScreen(
                onNavigateToLogin = {
                    navController.navigate(route = LoginDestination.route)
                },
                onItemClick = {
                    navController.navigate(PetDetailDestination.route)
                },
                navController = navController
            )
        }
        composable(LoginDestination.route) {
            LoginScreen(onNavigateToRegistration = {
                navController.navigate(route = RegistrationDestination.route)
            }, onNavigateToForgotPassword = { }, onNavigateToAuthenticatedRoute = {
                navController.navigate(route = HomeDestination.route)

            })
        }
        composable(RegistrationDestination.route) {
            RegistrationScreen(onNavigateBack = {
                navController.navigateUp()
            },
                onNavigateToAuthenticatedRoute = {
                    navController.navigate(route = HomeDestination.route)
                })
        }
        composable(PetDetailDestination.route) {
            PetDetailScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(ShelterDestination.route) {
            ShelterScreen(
                navController = navController
            )
        }
        composable(ProfileDestination.route) {
            ProfileScreen(
                navController = navController
            )
        }
        composable(EditProfileDestination.route) {
            EditProfile(onFinishEditing = { navController.navigateUp() })
        }
    }
}


sealed class BottomNavigationScreens(
    val route: String,
    val selectedIcon: ImageVector
) {
    object Home : BottomNavigationScreens(
        route = HomeDestination.route,
        selectedIcon = Icons.Default.Home
    )

    object Shelter : BottomNavigationScreens(
        route = ShelterDestination.route,
        selectedIcon = Icons.Default.LocationOn
    )

    object Profile : BottomNavigationScreens(
        route = ProfileDestination.route,
        selectedIcon = Icons.Default.Person
    )
}

val bottomNavigationItems = listOf(
    BottomNavigationScreens.Home,
    BottomNavigationScreens.Shelter,
    BottomNavigationScreens.Profile,
)

@Composable
fun BottomNavBar(
    navController: NavHostController
) {

    BottomAppBar(
        containerColor = primaryYellow
    ) {

        bottomNavigationItems.forEachIndexed { index, value ->
            val selected =
                navController.currentBackStackEntryAsState().value?.destination?.route == value.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(value.route)
                },
                icon = {
                    Icon(
                        imageVector = value.selectedIcon,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

interface NavigationDestination {
    val route: String
}