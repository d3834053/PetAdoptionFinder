package uk.ac.tees.mad.d3834053.presentation.petdetail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.ui.theme.primaryYellow

object PetDetailDestination : NavigationDestination {
    override val route = "petDetail"
    val petIdArg = "petId"
    val routeWithArg = "$route/{$petIdArg}"
}

@Composable
fun PetDetailScreen(
    onNavigateBack: () -> Unit
) {
    val petDetailViewModel: PetDetailViewModel = hiltViewModel()
    val item by petDetailViewModel.pet.collectAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.height((screenHeight / 2).dp)) {
            if (item.image.isEmpty()) {
                Image(
                    imageVector = Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).crossfade(true)
                        .data(item.image)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .padding(16.dp),
                colors = IconButtonDefaults.iconButtonColors(Color.White.copy(0.7f))
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .padding(24.dp)

        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Sex", fontSize = 16.sp)
                    Text(text = item.sex, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                }
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Age", fontSize = 16.sp)
                    Text(
                        text = "${item.age} years",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(text = "Weight", fontSize = 16.sp)
                    Text(
                        text = "${item.weight} kg",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                Text(text = item.name, fontWeight = FontWeight.SemiBold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Rounded.LocationOn,
                            contentDescription = null,
                            tint = primaryYellow
                        )
                        Text(text = item.address)
                    }
                    Text(text = item.dob)
                }
                Row(Modifier.padding(vertical = 12.dp)) {
                    Text(text = item.description)
                }
                Column {
                    Text(
                        text = "Contact person",
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.person),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            Column {
                                Text(
                                    text = item.contactPerson.name,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Owner", fontSize = 15.sp, color = Color.Gray)
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        context.dial(item.contactPerson.phone)
                                    }
                                    .background(Color.Blue),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(primaryYellow)
                                    .clickable {
                                        context.sendMail(
                                            item.contactPerson.email,
                                            "Want to pet ${item.name}"
                                        )
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Chat,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Context.dial(phone: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        startActivity(intent)
    } catch (t: Throwable) {
        Log.d("Dial Error ERROR", t.message.toString())
    }
}

fun Context.sendMail(to: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        val data = Uri.parse("mailto:${to}?subject=$subject&body=")
        intent.setData(data)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        this.showToast("No email app")
    } catch (t: Throwable) {
        Log.d("Message ERROR", t.message.toString())
    }
}

fun Context.showToast(text: String) {
    Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
}

