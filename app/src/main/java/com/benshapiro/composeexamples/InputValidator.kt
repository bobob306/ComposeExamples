package com.benshapiro.composeexamples

import androidx.core.text.isDigitsOnly

object InputValidator {
    fun getFirstNameErrorIdOrNull(input: String): String? {
        return when {
            input.isBlank() -> "Enter a first name"
            input.length < 2 -> "First name too short"
            else -> null
        }
    }
    // check some rules for the input
    // just an example so not too thorough here

    fun getAgeErrorIdOrNull(input: String): String? {
        return when {
            input.isEmpty() -> "Enter your age"
            input.isDigitsOnly().not() -> "Only enter digits"
            else -> null
        }
    }

    fun getLastNameErrorIdOrNull(input: String): String? {
        return when {
            input.isBlank() -> "Enter a last name"
            input.length < 2 -> "Last name too short"
            else -> null
        }
    }

    fun getTestErrorOrNull(input: String): String? {
        return when {
            input.isBlank() -> "Enter a test string "
            input.length in 1..3 -> "Test string too short"
            else -> null
        }
    }

}