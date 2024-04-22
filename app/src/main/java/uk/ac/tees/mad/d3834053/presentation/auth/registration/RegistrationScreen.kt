package uk.ac.tees.mad.d3834053.presentation.auth.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.ac.tees.mad.d3834053.NavigationDestination
import uk.ac.tees.mad.d3834053.presentation.common.SmallClickableWithIconAndText
import uk.ac.tees.mad.d3834053.presentation.common.TitleText
import uk.ac.tees.mad.d3834053.presentation.state.registration.RegistrationUiEvent
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.constants.GlobalConstants


object RegistrationDestination : NavigationDestination {
    override val route = "registration"
}

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit,
) {

    val registrationState by remember {
        registrationViewModel.registrationState
    }
    val context = LocalContext.current
    GlobalConstants.context = context

    if (registrationState.isRegistrationSuccessful) {
        LaunchedEffect(key1 = true) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    } else {
        // Full Screen Content
        Box(modifier = with(Modifier) {
            fillMaxSize()
            background(Color.White)

        }) {
            // Back Button Icon
            Row(
                modifier = Modifier
                    .clickable {
                        onNavigateBack()
                    }
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .padding(top = 10.dp)
                    .align(Alignment.TopStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = stringResource(id = R.string.navigate_back),
                    tint = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = stringResource(id = R.string.back_to_login),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
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

                // Heading Registration
                TitleText(
                    modifier = Modifier.padding(top = 10.dp),
                    text = stringResource(id = R.string.registration_heading_text)
                )

                /**
                 * Registration Inputs Composable
                 */

                /**
                 * Registration Inputs Composable
                 */
                RegistrationInputs(
                    registrationState = registrationState,
                    onEmailIdChange = { inputString ->
                        registrationViewModel.onUiEvent(
                            registrationUiEvent = RegistrationUiEvent.EmailChanged(
                                inputValue = inputString
                            )
                        )
                    },
                    onMobileNumberChange = { inputString ->
                        registrationViewModel.onUiEvent(
                            registrationUiEvent = RegistrationUiEvent.MobileNumberChanged(
                                inputValue = inputString
                            )
                        )
                    },
                    onPasswordChange = { inputString ->
                        registrationViewModel.onUiEvent(
                            registrationUiEvent = RegistrationUiEvent.PasswordChanged(
                                inputValue = inputString
                            )
                        )
                    },
                    onConfirmPasswordChange = { inputString ->
                        registrationViewModel.onUiEvent(
                            registrationUiEvent = RegistrationUiEvent.ConfirmPasswordChanged(
                                inputValue = inputString
                            )
                        )
                    },
                    onSubmit = {
                        registrationViewModel.onUiEvent(registrationUiEvent = RegistrationUiEvent.Submit)
                        onNavigateToAuthenticatedRoute()
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {

    RegistrationScreen(onNavigateBack = {}, onNavigateToAuthenticatedRoute = {})
}