package uk.ac.tees.mad.d3834053.presentation.state.login

import android.app.Activity
import uk.ac.tees.mad.d3834053.presentation.common.state.ErrorState


/**
 * Login State holding ui input values
 */
data class LoginState(
    val emailOrMobile: String ="",
    val password: String = "",
    val errorState: LoginErrorState = LoginErrorState(),
    var isLoginSuccessful: Boolean = false,
)

/**
 * Error state in login holding respective
 * text field validation errors
 */
data class LoginErrorState(
    val emailOrMobileErrorState: ErrorState = ErrorState(),
    val passwordErrorState: ErrorState = ErrorState()
)

