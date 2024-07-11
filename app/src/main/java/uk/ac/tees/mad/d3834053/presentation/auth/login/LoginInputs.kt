package uk.ac.tees.mad.d3834053.presentation.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.common.EmailTextField
import uk.ac.tees.mad.d3834053.presentation.common.PasswordTextField
import uk.ac.tees.mad.d3834053.presentation.state.login.LoginState

@Composable
fun LoginInputs(
    loginState: LoginState,
    onEmailOrMobileChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    // Login Inputs Section
    Column(modifier = Modifier.fillMaxWidth()) {

        // Email or Mobile Number
        EmailTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            value = loginState.emailOrMobile,
            onValueChange = onEmailOrMobileChange,
            label = stringResource(id = R.string.login_email_id),
            isError = loginState.errorState.emailOrMobileErrorState.hasError,
            errorText = stringResource(id = loginState.errorState.emailOrMobileErrorState.errorMessageStringResource)
        )


        // Password
        PasswordTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            value = loginState.password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.login_password_label),
            isError = loginState.errorState.passwordErrorState.hasError,
            errorText = stringResource(id = loginState.errorState.passwordErrorState.errorMessageStringResource),
            imeAction = ImeAction.Done
        )

        Spacer(modifier = Modifier.height(10.dp))
        // Login Submit Button

        Button(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(5.dp)
                .size(65.dp),
            onClick = onSubmit,
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFAE00))
        ) {
            Text(
                text = stringResource(id = R.string.login_button_text),
                style = MaterialTheme.typography.titleLarge
            )
        }

    }
}