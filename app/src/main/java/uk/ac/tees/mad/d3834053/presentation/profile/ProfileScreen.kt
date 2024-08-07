package uk.ac.tees.mad.d3834053.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import uk.ac.tees.mad.d3834053.BottomNavBar
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.presentation.auth.login.LoginDestination
import uk.ac.tees.mad.d3834053.ui.theme.primaryYellow

object ProfileDestination : NavigationDestination {
    override val route = "profile"
}

@Composable
fun ProfileScreen(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val userProfileState by profileViewModel.userStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.fetchUserDetails()
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
            Column {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(primaryYellow)
                ) {
                    Text(
                        text = "Profile",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = { navController.navigate(EditProfileDestination.route) },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(horizontal = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = "Edit")
                        }
                        Box(
                            Modifier
                                .align(Alignment.Center)
                                .clip(CircleShape)
                                .border(1.dp, Color.Black, CircleShape)
                                .size(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (userProfileState.image.isNullOrEmpty()) {
                                Image(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(12.dp)
                                        .clip(CircleShape)
                                )
                            } else {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .crossfade(true)
                                        .data(userProfileState.image)
                                        .build(),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = userProfileState.name,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp
                    )
                    Firebase.auth.currentUser?.email?.let { Text(text = it) }
                    Text(
                        text = userProfileState.address,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)
                    ) {
                        Text(text = "Logout")
                    }
                }
            }

        }
    }
}