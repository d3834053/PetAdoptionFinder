package uk.ac.tees.mad.d3834053.presentation.auth.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()


    private var repository = AuthRepository()

    fun checkUser(user: FirebaseUser? = null) {
        if (user != null) {
            _loginState.value = _loginState.value.copy(isLoginSuccessful = true)
        }
    }

    fun signOut() {
        _loginState.value = LoginState()
        Firebase.auth.signOut()
    }

    fun onUiEvent(loginUiEvent: LoginUiEvent) {
        when (loginUiEvent) {
            is LoginUiEvent.EmailOrMobileChanged -> {
                _loginState.value = _loginState.value.copy(
                    emailOrMobile = loginUiEvent.inputValue,
                    errorState = _loginState.value.errorState.copy(
                        emailOrMobileErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            emailOrMobileEmptyErrorState
                    )
                )
            }

            is LoginUiEvent.PasswordChanged -> {
                _loginState.value = _loginState.value.copy(
                    password = loginUiEvent.inputValue,
                    errorState = _loginState.value.errorState.copy(
                        passwordErrorState = if (loginUiEvent.inputValue.trim().isNotEmpty())
                            ErrorState()
                        else
                            passwordEmptyErrorState
                    )
                )
            }

            is LoginUiEvent.Submit -> {
                // Only attempt login if inputs are valid
                if (validateInputs()) {
                    loginUser(GlobalConstants.context!!)
                } else {
                    // Handle invalid input case, e.g., show an error message
                }
            }
        }
    }

    private fun loginUser(context: Context) = viewModelScope.launch {
        try {
            repository.login(
                _loginState.value.emailOrMobile,
                _loginState.value.password
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(context, "Success Login", Toast.LENGTH_SHORT).show()
                    _loginState.value = _loginState.value.copy(isLoginSuccessful = true)
                } else {
                    Toast.makeText(context, "Failed Login", Toast.LENGTH_SHORT).show()
                    _loginState.value = LoginState()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun validateInputs(): Boolean {
        val emailOrMobileString = _loginState.value.emailOrMobile.trim()
        val passwordString = _loginState.value.password.trim()
        return when {
            emailOrMobileString.isEmpty() -> {
                _loginState.value = _loginState.value.copy(
                    errorState = LoginErrorState(
                        emailOrMobileErrorState = emailOrMobileEmptyErrorState
                    )
                )
                false
            }

            passwordString.isEmpty() -> {
                _loginState.value = _loginState.value.copy(
                    errorState = LoginErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            else -> {
                _loginState.value = _loginState.value.copy(errorState = LoginErrorState())
                true
            }
        }
    }
}