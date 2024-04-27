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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
    navController: NavHostController
) {
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
                    Box(
                        Modifier
                            .border(1.dp, Color.Black, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
//                    if (item != null) {
//                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current).crossfade(true)
//                                .data(item.imageRes)
//                                .build(),
//                            contentDescription = null,
//                            modifier = Modifier.fillMaxWidth(),
//                            contentScale = ContentScale.Crop
//                        )
//                    } else {
                        Image(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(12.dp)
                                .clip(CircleShape)
                        )
//                    }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "User name", fontWeight = FontWeight.Medium, fontSize = 24.sp)
                    Text(text = "user@email.com")

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Edit profile",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    navController.navigate(EditProfileDestination.route)
                                }
                        )
                        Text(
                            text = "Log out",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    Firebase.auth.signOut()
                                    navController.navigate(LoginDestination.route)
                                }
                        )
                    }
                }
            }

        }
    }
}