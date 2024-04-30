package uk.ac.tees.mad.d3834053.presentation.state.login

import uk.ac.tees.mad.d3834053.R
import uk.ac.tees.mad.d3834053.presentation.common.state.ErrorState


val emailOrMobileEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_email_mobile
)

val passwordEmptyErrorState = ErrorState(
    hasError = true,
    errorMessageStringResource = R.string.login_error_msg_empty_password
)
