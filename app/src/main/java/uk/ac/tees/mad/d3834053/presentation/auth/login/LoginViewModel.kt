package uk.ac.tees.mad.d3834053.presentation.auth.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3834053.presentation.auth.repository.AuthRepository
import uk.ac.tees.mad.d3834053.presentation.common.state.ErrorState
import uk.ac.tees.mad.d3834053.presentation.constants.GlobalConstants
import uk.ac.tees.mad.d3834053.presentation.state.login.LoginErrorState
import uk.ac.tees.mad.d3834053.presentation.state.login.LoginState
import uk.ac.tees.mad.d3834053.presentation.state.login.LoginUiEvent
import uk.ac.tees.mad.d3834053.presentation.state.login.emailOrMobileEmptyErrorState
import uk.ac.tees.mad.d3834053.presentation.state.login.passwordEmptyErrorState

/**
 * ViewModel for Login Screen
 */
class LoginViewModel() : ViewModel() {

    var loginState = mutableStateOf(LoginState())
        private set

    private var repository = AuthRepository()
    /**
     * Function called on any login event [LoginUiEvent]
     */
    fun checkUser(user: FirebaseUser? = null){
        if(user != null){
            loginState.value.isLoginSuccessful = true
        }
        return
    }
    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {

            // Email/Mobile changed
            is LoginUiEvent.EmailOrMobileChanged -> {
                loginState.value = loginState.value.copy(
                    emailOrMobile = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        emailOrMobileErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailOrMobileEmptyErrorState
                    )
                )
            }

            // Password changed
            is LoginUiEvent.PasswordChanged -> {
                loginState.value = loginState.value.copy(
                    password = loginUiEvent.inputValue,
                    errorState = loginState.value.errorState.copy(
                        passwordErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            // Submit Login
            is LoginUiEvent.Submit -> {
                loginUser(GlobalConstants.context!!)

            }
        }
    }
    private fun loginUser(context: Context) = viewModelScope.launch {
        try {

            if (!validateInputs()) {
                throw IllegalArgumentException("email and password can not be empty")
            }

            repository.login(
                loginState.value.emailOrMobile,
                loginState.value.password
                ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginState.value.isLoginSuccessful = true
                } else {
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginState.value.isLoginSuccessful = false
                }

            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    /**
     * Function to validate inputs
     * Ideally it should be on domain layer (usecase)
     * @return true -> inputs are valid
     * @return false -> inputs are invalid
     */
    private fun validateInputs(): Boolean {
        val emailOrMobileString = loginState.value.emailOrMobile.trim()
        val passwordString = loginState.value.password
        return when {

            // Email/Mobile empty
            emailOrMobileString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        emailOrMobileErrorState = emailOrMobileEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                loginState.value = loginState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                loginState.value = loginState.value.copy(errorState = LoginErrorState())
                true
            }
        }
    }

}