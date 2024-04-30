package uk.ac.tees.mad.d3834053.presentation.auth.login

import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.common.TitleText
import uk.ac.tees.mad.d3834053.presentation.constants.GlobalConstants
import uk.ac.tees.mad.d3834053.presentation.state.login.LoginUiEvent


object LoginDestination : NavigationDestination {
    override val route = "login"
}


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onNavigateToRegistration: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit,
) {
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val loginState =
        loginViewModel.loginState.collectAsState()

//    val launcher = rememberFirebaseAuthLauncher(
//        onAuthComplete = { result ->
//            user = result.user
//            GlobalConstants.user = user
//        },
//        onAuthError = {
//            user = null
//        }
//    )
//    val token = stringResource(id = R.string.your_web_client_id)
    val context = LocalContext.current
    GlobalConstants.context = context
    //to check user is already login or not.
    loginViewModel.checkUser(user)

    LaunchedEffect(loginState.value) {
        println("LaunchedEffect triggered with login success: ${loginState.value}")
        if (loginState.value.isLoginSuccessful) {
            println("Navigating to authenticated route")
            onNavigateToAuthenticatedRoute.invoke()
        }
    }

    // Full Screen Content
    Box(modifier = with(Modifier) {
        fillMaxSize()
        background(Color.White)

    }) {

        Box(modifier = with(Modifier) {
            fillMaxWidth()
            background(Color.White)
            align(Alignment.TopEnd)

        }) {
            Image(
                modifier = Modifier
                    .size(200.dp, 200.dp),
                painter = painterResource(id = R.drawable.signup_background),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.TopEnd
            )
        }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .imePadding()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            // Heading Login
            TitleText(
                modifier = Modifier.padding(top = 12.dp),
                text = stringResource(id = R.string.login_heading_text),
            )
            Spacer(modifier = Modifier.size(0.dp, 10.dp))

            Text(
                text = stringResource(id = R.string.login_subheading_text),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.size(0.dp, 20.dp))


            // Login Inputs Composable
            LoginInputs(
                loginState = loginState.value,
                onEmailOrMobileChange = { inputString ->
                    loginViewModel.onUiEvent(
                        loginUiEvent = LoginUiEvent.EmailOrMobileChanged(
                            inputString
                        )
                    )
                },
                onPasswordChange = { inputString ->
                    loginViewModel.onUiEvent(
                        loginUiEvent = LoginUiEvent.PasswordChanged(
                            inputString
                        )
                    )
                },
                onSubmit = {
                    loginViewModel.onUiEvent(loginUiEvent = LoginUiEvent.Submit)
                },
                onForgotPasswordClick = onNavigateToForgotPassword
            )

            Spacer(modifier = Modifier.size(0.dp, 30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                HorizontalDivider(Modifier.size(30.dp, 4.dp))
                Spacer(modifier = Modifier.size(10.dp, 0.dp))
                Text(text = "or")
                Spacer(modifier = Modifier.size(10.dp, 0.dp))
                HorizontalDivider(Modifier.size(30.dp, 4.dp))

            }

            // Register Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Don't have an account?
                Text(text = stringResource(id = R.string.do_not_have_account))

                //Register
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable {
                            onNavigateToRegistration.invoke()
                        },
                    text = stringResource(id = R.string.register),
                    color = Color(0xFFFFAE00)
                )
            }
        }
    }
}

//@Composable
//fun rememberEmailandpassword(
//    onAuthComplete: (AuthResult) -> Unit,
//    onAuthError: (Exception) -> Unit,
//): ManagedActivityResultLauncher<Intent, ActivityResult> {
//    val scope = rememberCoroutineScope()
//    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        try {
//            val credential = EmailAuthProvider.getCredential(
//                GlobalConstants.email_user?.email!!,
//                GlobalConstants.email_user?.password!!
//            )
//            scope.launch {
//                val result =
//                    Firebase.auth.currentUser!!.linkWithCredential(credential).await()
//                Log.d("GoogleAuth", "Signin Successful")
//
//                onAuthComplete(result)
//            }
//        } catch (e: Exception) {
//            Log.d("GoogleAuth", e.toString())
//            onAuthError(e)
//        }
//    }
//
//}

@Composable
fun rememberFirebaseAuthLauncher(
    onAuthComplete: (AuthResult) -> Unit,
    onAuthError: (ApiException) -> Unit,
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    val scope = rememberCoroutineScope()
    return rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            Log.d("GoogleAuth", "account $account")
            val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
            scope.launch {
                val authResult = Firebase.auth.signInWithCredential(credential).await()
                onAuthComplete(authResult)
            }
        } catch (e: ApiException) {
            Log.d("GoogleAuth", e.toString())
            onAuthError(e)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginScreen() {
//    LoginScreen(onNavigateToRegistration = {}, onNavigateToForgotPassword = { }) {}
//}