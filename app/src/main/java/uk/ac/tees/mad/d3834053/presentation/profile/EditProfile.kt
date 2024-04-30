package uk.ac.tees.mad.d3834053.presentation.profile

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CameraEnhance
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.ui.theme.primaryYellow
import uk.ac.tees.mad.d3881495.utils.ApplicationViewModel
import uk.ac.tees.mad.d3881495.utils.LocationRepository

object EditProfileDestination : NavigationDestination {
    override val route: String = "edit_profile"
}

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(
    onFinishEditing: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val currentUserState by profileViewModel.userStateFlow.collectAsState(initial = null)

    val editState by profileViewModel.profileUIState.collectAsState()
    val modifyUserState by profileViewModel.userUpdateFlow.collectAsState(initial = null)
    val applicationContext = LocalContext.current
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    LaunchedEffect(Unit) {
        profileViewModel.fetchUserDetails()
    }
    LaunchedEffect(currentUserState) {
        currentUserState?.let {
            val item = it
            launch(Dispatchers.IO) {
                profileViewModel.modifyUserProfileData(
                    UserProfile(
                        name = item.name,
                        email = item.email,
                        image = fetchImageFromURL(item.image ?: ""),
                        address = item.address,
                    )
                )
            }
        }
    }

    // Handling image selection
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val galleryImagePicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
            uri?.let { profileViewModel.handleImageSelection(it, applicationContext) }
        }

    val cameraImageCapture =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
            bitmap?.let { profileViewModel.handleImageCapture(it) }
        }

    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
    val mainViewModel: ApplicationViewModel = hiltViewModel()
    val isGpsEnabled = remember { mutableStateOf(false) }
    val locationRepo = LocationRepository(
        context = applicationContext, activity = (applicationContext as Activity)
    )
    LaunchedEffect(key1 = true) {
        isGpsEnabled.value = locationRepo.gpsStatus.first()
    }

    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(primaryYellow), verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onFinishEditing) {
                Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
            }
            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                Text(text = "Edit Profile", fontSize = 19.sp, fontWeight = FontWeight.Medium)
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState,
                windowInsets = WindowInsets.ime
            ) {
                // Content for bottom sheet
                SelectionForImageSource(chooseGallery = {
                    coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                    galleryImagePicker.launch("image/*")
                }, chooseCamera = {
                    coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                    if (cameraPermission.status.isGranted) {
                        cameraImageCapture.launch(null)
                    } else {
                        cameraPermission.launchPermissionRequest()
                        if (cameraPermission.status.isGranted) {
                            cameraImageCapture.launch(null)
                        }
                    }
                })
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            UploadPhotoButton(
                onClick = { showBottomSheet = true }, image = editState.image
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            TextFieldWithLabel(
                label = "Name",
                text = editState.name,
                onTextChange = { profileViewModel.modifyUserProfileData(editState.copy(name = it)) },
                focusManager = focusManager
            )
            LocationInputField(
                label = "Location",
                text = editState.address,
                onTextChange = { profileViewModel.modifyUserProfileData(editState.copy(address = it)) },
                focusManager = focusManager,
                locationRepo = locationRepo,
                mainViewModel = mainViewModel,
                isGpsEnabled = isGpsEnabled.value
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    profileViewModel.submitProfileUpdate(context = applicationContext)
                    onFinishEditing.invoke()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Update")
            }
        }
    }
}

@Composable
fun TextFieldWithLabel(
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    focusManager: FocusManager,
    isFinal: Boolean = false,
    endIcon: @Composable (() -> Unit)? = null
) {
    Column {
        Text(text = label)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(),
            placeholder = { Text(text = label) },
            keyboardOptions = KeyboardOptions(imeAction = if (isFinal) ImeAction.Done else ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }),
            trailingIcon = endIcon
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationInputField(
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    focusManager: FocusManager,
    locationRepo: LocationRepository,
    mainViewModel: ApplicationViewModel,
    isGpsEnabled: Boolean
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val locationPermission =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)



    TextFieldWithLabel(label = label,
        text = text,
        onTextChange = onTextChange,
        focusManager = focusManager,
        endIcon = {
            IconButton(onClick = {
                if (locationPermission.status.isGranted) {
                    if (isGpsEnabled) {
                        // Retrieve and update location
                        coroutineScope.launch {
                            mainViewModel.locationFlow.collectLatest { location ->
                                val locationAddress = locationRepo.getAddressFromCoordinate(
                                    location.latitude, location.longitude
                                )
                                println(locationAddress)
                                onTextChange(locationAddress)
                            }
                        }
                    } else {
                        // Prompt to enable GPS
                        Toast.makeText(context, "Enable GPS for location", Toast.LENGTH_SHORT)
                            .show()
                        locationRepo.checkGpsSettings()
                    }
                } else {
                    locationPermission.launchPermissionRequest()
                }
            }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Get Location")
            }
        })
}

@Composable
fun UploadPhotoButton(
    onClick: () -> Unit, image: ByteArray?
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .height(100.dp)
            .aspectRatio(1f),
        shape = CircleShape,
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (image == null) {
                Icon(
                    imageVector = Icons.Rounded.AddAPhoto,
                    contentDescription = "Upload image",
                    modifier = Modifier.size(40.dp)
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).crossfade(true).data(image)
                        .build(),
                    contentDescription = "Uploaded image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
    }
}

@Composable
fun SelectionForImageSource(
    chooseCamera: () -> Unit, chooseGallery: () -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier
            .clickable { chooseCamera() }
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CameraEnhance,
                contentDescription = "Camera option",
                modifier = Modifier
                    .padding(16.dp)
                    .size(35.dp)
            )

            Text(
                text = "Camera", modifier = Modifier.padding(16.dp), fontSize = 16.sp
            )
        }
        Row(modifier = Modifier
            .clickable { chooseGallery() }
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "Gallery option",
                modifier = Modifier
                    .padding(16.dp)
                    .size(35.dp)
            )
            Text(
                text = "Gallery", modifier = Modifier.padding(16.dp), fontSize = 16.sp
            )
        }
    }
}