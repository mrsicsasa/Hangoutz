package com.example.hangoutz.utils

object Validator {

    fun areAllFieldsFilled(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Array<Boolean> {
        val errors = arrayOf(false, false, false, false)
        if (name.isEmpty()) {
            errors[0] = true
        }

        if (email.isEmpty()) {
            errors[1] = true
        }

        if (password.isEmpty()) {
            errors[2] = true
        }

        if (confirmPassword.isEmpty()) {
            errors[3] = true
        }
        return errors
    }

    fun isValidNameLength(name: String): Boolean {
        return (name.length >= Constants.MIN_NAME_LENGTH && name.length <= Constants.MAX_NAME_LENGTH)
    }

    fun isValidEmail(email: String): Boolean {
        return (email.endsWith(Constants.VALID_EMAIL) == true &&
                !email.startsWith(Constants.AT_SIGN) &&
                email.split(Constants.AT_SIGN).size == Constants.TWO_HALVES)
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length >= Constants.MIN_PASSWORD_LENGTH) {
            val regex = Regex(Constants.CONTAINS_DIGIT)
            return regex.containsMatchIn(password)
        }
        return false
    }

    fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return (password == confirmPassword)
    }
}