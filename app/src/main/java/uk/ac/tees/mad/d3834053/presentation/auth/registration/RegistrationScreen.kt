package uk.ac.tees.mad.d3834053.presentation.auth.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.common.TitleText
import uk.ac.tees.mad.d3834053.presentation.constants.GlobalConstants
import uk.ac.tees.mad.d3834053.presentation.state.registration.RegistrationUiEvent

object RegistrationDestination : NavigationDestination {
    override val route = "registration"
}

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToAuthenticatedRoute: () -> Unit,
) {

    val registrationState by
    registrationViewModel.registrationState.collectAsState()

    val context = LocalContext.current
    GlobalConstants.context = context

    LaunchedEffect(registrationState) {
        if (registrationState.isRegistrationSuccessful) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    }
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
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistrationScreen() {

    RegistrationScreen(onNavigateBack = {}, onNavigateToAuthenticatedRoute = {})
}