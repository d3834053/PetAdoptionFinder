package uk.ac.tees.mad.d3834053.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.d3834053.R

/**
 * Password Text Field
 */
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorText: String = "",
    imeAction: ImeAction = ImeAction.Done
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, color = Color.Black)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Lock, contentDescription = "email")
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFFAE00),
            unfocusedBorderColor = Color(0xFFFFAE00),
            focusedLabelColor = Color(0xFFFFAE00),
            errorSupportingTextColor = Color(0xFFC65B52),
            errorBorderColor = Color(0xFFC65B52),
            errorSuffixColor = Color(0xFFC65B52),
            errorLabelColor = Color(0xFFC65B52),
            errorLeadingIconColor = Color(0xFFC65B52),
            errorTextColor = Color(0xFFC65B52),
        ),
        shape = RoundedCornerShape(24.dp),
        trailingIcon = {
            IconButton(onClick = {
                isPasswordVisible = !isPasswordVisible
            }) {

                val visibleIconAndText = Pair(
                    first = Icons.Filled.Visibility,
                    second = stringResource(id = R.string.icon_password_visible)
                )

                val hiddenIconAndText = Pair(
                    first = Icons.Filled.VisibilityOff,
                    second = stringResource(id = R.string.icon_password_hidden)
                )

                val passwordVisibilityIconAndText =
                    if (isPasswordVisible) visibleIconAndText else hiddenIconAndText

                // Render Icon
                Icon(
                    imageVector = passwordVisibilityIconAndText.first,
                    contentDescription = passwordVisibilityIconAndText.second
                )
            }
        },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        isError = isError,
        supportingText = {
            if (isError) {
                ErrorTextInputField(text = errorText)
            }
        }
    )
}

/**
 * Email Text Field
 */
@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorText: String = "",
    imeAction: ImeAction = ImeAction.Next
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(24.dp),
        label = {
            Text(text = label, color = Color.Black)
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFFAE00),
            unfocusedBorderColor = Color(0xFFFFAE00),
            focusedLabelColor = Color(0xFFFFAE00),
            errorSupportingTextColor = Color(0xFFC65B52),
            errorBorderColor = Color(0xFFC65B52),
            errorSuffixColor = Color(0xFFC65B52),
            errorLabelColor = Color(0xFFC65B52),
            errorLeadingIconColor = Color(0xFFC65B52),
            errorTextColor = Color(0xFFC65B52),


            ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                ErrorTextInputField(text = errorText)
            }
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Email, contentDescription = "email")
        }

    )

}

/**
 * Mobile Number Text Field
 */
@Composable
fun MobileNumberTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorText: String = "",
    imeAction: ImeAction = ImeAction.Next
) {

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(text = label, color = Color.Black)
        },
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFFAE00),
            unfocusedBorderColor = Color(0xFFFFAE00),
            focusedLabelColor = Color(0xFFFFAE00),
            errorSupportingTextColor = Color(0xFFC65B52),
            errorBorderColor = Color(0xFFC65B52),
            errorSuffixColor = Color(0xFFC65B52),
            errorLabelColor = Color(0xFFC65B52),
            errorLeadingIconColor = Color(0xFFC65B52),
            errorTextColor = Color(0xFFC65B52),


            ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = imeAction
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                ErrorTextInputField(text = errorText)
            }
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Call, contentDescription = "email")
        }
    )

}