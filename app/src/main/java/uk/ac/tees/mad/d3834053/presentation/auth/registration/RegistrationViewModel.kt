package uk.ac.tees.mad.d3834053.presentation.auth.registration

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.auth.repository.AuthRepository
import uk.ac.tees.mad.d3834053.presentation.common.state.ErrorState
import uk.ac.tees.mad.d3834053.presentation.constants.GlobalConstants
import uk.ac.tees.mad.d3834053.presentation.state.registration.RegistrationErrorState
import uk.ac.tees.mad.d3834053.presentation.state.registration.RegistrationState
import uk.ac.tees.mad.d3834053.presentation.state.registration.RegistrationUiEvent
import uk.ac.tees.mad.d3834053.presentation.state.registration.confirmPasswordEmptyErrorState
import uk.ac.tees.mad.d3834053.presentation.state.registration.emailEmptyErrorState
import uk.ac.tees.mad.d3834053.presentation.state.registration.mobileNumberEmptyErrorState
import uk.ac.tees.mad.d3834053.presentation.state.registration.passwordEmptyErrorState
import uk.ac.tees.mad.d3834053.presentation.state.registration.passwordMismatchErrorState

class RegistrationViewModel : ViewModel() {

    private val _registrationState = MutableStateFlow(RegistrationState())
    val registrationState = _registrationState.asStateFlow()

    var repository = AuthRepository()

    /**
     * Function called on any login event [RegistrationUiEvent]
     */
    fun onUiEvent(registrationUiEvent: RegistrationUiEvent) {
        when (registrationUiEvent) {

            // Email id changed event
            is RegistrationUiEvent.EmailChanged -> {
                _registrationState.value = _registrationState.value.copy(
                    emailId = registrationUiEvent.inputValue,
                    errorState = _registrationState.value.errorState.copy(
                        emailIdErrorState = if (registrationUiEvent.inputValue.trim().isEmpty()) {
                            // Email id empty state
                            emailEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Mobile Number changed event
            is RegistrationUiEvent.MobileNumberChanged -> {
                _registrationState.value = _registrationState.value.copy(
                    mobileNumber = registrationUiEvent.inputValue,
                    errorState = _registrationState.value.errorState.copy(
                        mobileNumberErrorState = if (registrationUiEvent.inputValue.trim()
                                .isEmpty()
                        ) {
                            // Mobile Number Empty state
                            mobileNumberEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Password changed event
            is RegistrationUiEvent.PasswordChanged -> {
                _registrationState.value = _registrationState.value.copy(
                    password = registrationUiEvent.inputValue,
                    errorState = _registrationState.value.errorState.copy(
                        passwordErrorState = if (registrationUiEvent.inputValue.trim().isEmpty()) {
                            // Password Empty state
                            passwordEmptyErrorState
                        } else {
                            // Valid state
                            ErrorState()
                        }

                    )
                )
            }

            // Confirm Password changed event
            is RegistrationUiEvent.ConfirmPasswordChanged -> {
                _registrationState.value = _registrationState.value.copy(
                    confirmPassword = registrationUiEvent.inputValue,
                    errorState = _registrationState.value.errorState.copy(
                        confirmPasswordErrorState = when {

                            // Empty state of confirm password
                            registrationUiEvent.inputValue.trim().isEmpty() -> {
                                confirmPasswordEmptyErrorState
                            }

                            // Password is different than the confirm password
                            _registrationState.value.password.trim() != registrationUiEvent.inputValue -> {
                                passwordMismatchErrorState
                            }

                            // Valid state
                            else -> ErrorState()
                        }
                    )
                )
            }


            // Submit Registration event
            is RegistrationUiEvent.Submit -> {
                val inputsValidated = validateInputs()
                Log.d("VLAID", "${inputsValidated} INPU")
                if (inputsValidated) {
//                    _registrationState.value =
//                        _registrationState.value.copy(isRegistrationSuccessful = true)
                    SignupUser(GlobalConstants.context!!)
                } else {
//                    _registrationState.value =
//                        _registrationState.value.copy(isRegistrationSuccessful = false)
                }
            }
        }
    }

    private fun SignupUser(context: Context) = viewModelScope.launch {
        try {

            if (!validateInputs()) {
                throw IllegalArgumentException("email and password can not be empty")
            }

            repository.createUser(
                _registrationState.value.emailId,
                _registrationState.value.password
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    _registrationState.value =
                        _registrationState.value.copy(isRegistrationSuccessful = true)

                } else {
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    _registrationState.value =
                        _registrationState.value.copy(isRegistrationSuccessful = false)
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
        val emailString = _registrationState.value.emailId.trim()
        val mobileNumberString = _registrationState.value.mobileNumber.trim()
        val passwordString = _registrationState.value.password.trim()
        val confirmPasswordString = _registrationState.value.confirmPassword.trim()

        // Regex pattern for validating email
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return when {
            // Check if email matches pattern
            !emailString.matches(emailPattern.toRegex()) -> {
                _registrationState.value = _registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        emailIdErrorState = ErrorState(
                            hasError = true,
                            errorMessageStringResource = R.string.invalid_email_format
                        )
                    )
                )
                false
            }

            // Email empty
            emailString.isEmpty() -> {
                _registrationState.value = _registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        emailIdErrorState = emailEmptyErrorState
                    )
                )
                false
            }

            //Mobile Number Empty
            mobileNumberString.isEmpty() -> {
                _registrationState.value = _registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        mobileNumberErrorState = mobileNumberEmptyErrorState
                    )
                )
                false
            }

            //Password Empty
            passwordString.isEmpty() -> {
                _registrationState.value = _registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        passwordErrorState = passwordEmptyErrorState
                    )
                )
                false
            }

            //Confirm Password Empty
            confirmPasswordString.isEmpty() -> {
                _registrationState.value = _registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        confirmPasswordErrorState = confirmPasswordEmptyErrorState
                    )
                )
                false
            }

            // Password and Confirm Password are different
            passwordString != confirmPasswordString -> {
                _registrationState.value = _registrationState.value.copy(
                    errorState = RegistrationErrorState(
                        confirmPasswordErrorState = passwordMismatchErrorState
                    )
                )
                false
            }

            // No errors
            else -> {
                // Set default error state
                _registrationState.value =
                    _registrationState.value.copy(errorState = RegistrationErrorState())
                true
            }
        }
    }
}