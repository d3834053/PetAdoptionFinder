package uk.ac.tees.mad.d3834053.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import uk.ac.tees.mad.d3834053.NavigationDestination

object HomeDestination: NavigationDestination {
    override val route = "home"
}

@Composable
fun HomeScreen() {
    Text(text = "Home")
}