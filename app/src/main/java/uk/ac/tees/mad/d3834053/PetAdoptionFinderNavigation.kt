package uk.ac.tees.mad.d3834053

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.d3834053.presentation.SplashDestination
import uk.ac.tees.mad.d3834053.presentation.SplashScreen
import uk.ac.tees.mad.d3834053.presentation.auth.login.LoginDestination
import uk.ac.tees.mad.d3834053.presentation.auth.login.LoginScreen
import uk.ac.tees.mad.d3834053.presentation.auth.registration.RegistrationDestination
import uk.ac.tees.mad.d3834053.presentation.auth.registration.RegistrationScreen
import uk.ac.tees.mad.d3834053.presentation.home.HomeDestination
import uk.ac.tees.mad.d3834053.presentation.home.HomeScreen
import uk.ac.tees.mad.d3834053.presentation.onboarding.OnboardScreen
import uk.ac.tees.mad.d3834053.presentation.onboarding.OnboardingDestination
import uk.ac.tees.mad.d3834053.presentation.petdetail.PetDetailDestination
import uk.ac.tees.mad.d3834053.presentation.petdetail.PetDetailScreen

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
                }
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
    }
}

interface NavigationDestination {
    val route: String
}