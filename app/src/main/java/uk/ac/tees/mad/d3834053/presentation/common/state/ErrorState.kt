package uk.ac.tees.mad.d3834053.presentation.common.state

import androidx.annotation.StringRes
import uk.ac.tees.mad.d3834053.R

data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMessageStringResource: Int = R.string.empty_string
)